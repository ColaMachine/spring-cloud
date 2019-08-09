package com.dozenx.common.util;

import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelUtil {


    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Map<Integer, String>> getExcelDataKeyIndex(File file) throws Exception {
        Sheet sheet = getExcelSheetFromFile(file);
        List<Map<Integer, String>> retDats = getListFromSheet(sheet);
        return retDats;
    }

    /**
     * 返回的是有key名的数据
     *
     * @param file
     * @param keys
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getExcelDataKeyName(File file, List<String> keys) throws Exception {

        Sheet sheet = getExcelSheetFromFile(file);
        List<Map<String, String>> retDats = getListFromSheet(sheet, keys);

        return retDats;
    }

    /**
     * 返回的是有key名的数据
     *
     * @param fileInputStream
     * @param keys
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getExcelDataFromStream(InputStream fileInputStream, List<String> keys) throws Exception {

        Sheet sheet = getExcelSheetFromInputStream(fileInputStream);
        List<Map<String, String>> retDats = getListFromSheet(sheet, keys);

        return retDats;
    }

    /**
     * 将一个EXCEL文件转化为数据集合（以表头每列为key，对应列内容为value的list集合）
     *
     * @param file 文件
     * @return List
     * @throws IOException 抛出异常
     * @author 宋展辉
     */
    public static List<Map<String, String>> getExcelData(File file) throws IOException {
        List<Map<String, String>> retDats = new ArrayList<Map<String, String>>();
        FileInputStream finput = new FileInputStream(file);
        try {
            Workbook wb = getWorkBook(finput, file.getName());
            Sheet sheet = wb.getSheetAt(0);
            int firsRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            Row headerRow = sheet.getRow(firsRowNum);
            if (headerRow == null) {
                throw new IOException("excel头部数据缺失 请按模板使用");
            }
            List<String> header = getHeader(headerRow);
            for (int i = firsRowNum + 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                Map<String, String> eRow = new LinkedHashMap<String, String>();
                for (int j = 0; j < header.size(); j++) {
                    Cell cell = row.getCell(j);
                    String key = (String) header.get(j);
                    String value = getCellValue(cell);
                    eRow.put(key, value);
                }
                retDats.add(eRow);
            }
        } finally {
            finput.close();
        }
        return retDats;
    }

    /**
     * 将excel文件转化为javaBean
     *
     * @param file      文件
     * @param valueType 值类型
     * @param colMatch  参数 excel列名为key，字段名为value的Map
     * @param <T>       对象
     * @return List
     * @throws Exception   抛出异常
     * @throws IOException 抛出IO异常
     * @author 宋展辉
     */
    public static <T> List<T> getExcelDataToBean(File file, Class<T> valueType, Map<String, String> colMatch)
            throws IOException {
        // 生成excel对应的初始数据
        List<Map<String, String>> list = getExcelData(file);
        // 如果有匹配需要替换的列名（因为EXCEL导入一般使用的是中文，导入数据库则是英文），则进行key名的替换
        if (colMatch != null) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> excelMap = list.get(i);
                Map<String, String> dataMap = new HashMap<String, String>();
                for (String key : excelMap.keySet()) {
                    String dataKey = colMatch.get(key);
                    if (dataKey != null) {
                        dataMap.put(dataKey, excelMap.get(key));
                    }
                }
                list.set(i, dataMap);
            }
        }
        // 最后将Map转化为javaBean
        List<T> relists = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            String json = com.dozenx.common.util.JsonUtil.toJsonString(list.get(i));
            T object = JsonUtil.toJavaBean(json, valueType);
            relists.add(object);
        }
        return relists;
    }

    /**
     * 将一个Excel文件转化为Map（以对应表格位置为key，表格内容为value的Map）
     *
     * @param file 文件
     * @return Map
     * @throws IOException 抛出异常
     * @author 宋展辉
     */
    public static Map<String, String> getExcelMData(File file) throws IOException {
        Map<String, String> retDats = new HashMap<String, String>();
        FileInputStream finput = new FileInputStream(file);
        try {
            Workbook wb = getWorkBook(finput, file.getName());
            Sheet sheet = wb.getSheetAt(0);
            int firsRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            for (int i = firsRowNum; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getLastCellNum();
                    for (int j = firstCellNum; j < lastCellNum; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            String key = getKey(cell);
                            String value = getCellValue(cell);
                            if ((key != null) && (key.trim().length() > 0)) {
                                retDats.put(key, value);
                            }
                        }
                    }
                }
            }
        } finally {
            finput.close();
        }
        return retDats;
    }

    /**
     * 生成EXCEL文件
     *
     * @param obj      实体类集合
     * @param filePath 自定义文件路径
     * @param colTitle 匹配的有序列名，可以为空
     * @return File
     * @throws IOException 抛出异常
     * @author 宋展辉 2015年11月6日 下午3:19:39
     */
    public static File getExcelFile(List<?> obj, String filePath, LinkedHashMap<String, String> colTitle)
            throws IOException {
        if (obj == null || obj.size() == 0) {
            return null;
        } else {
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            for (int i = 0; i < obj.size(); i++) {
                // 将object转化为Map<String,String>
                data.add(convertObjToMap(obj.get(i)));
            }
            return getExcelFileFromMap(data, filePath, colTitle);
        }
    }

    /**
     * 将object转化为Map<String,String>
     *
     * @param object 参数
     * @return Map
     * @author 宋展辉 2015年11月6日 下午3:27:48
     */
    private static Map<String, String> convertObjToMap(Object object) {
        Map<String, String> map = new HashMap<String, String>();
        if (object == null) {// 若为空则返回空
            return null;
        } else if (object instanceof Map) {// 若本身就是一个Map对象，则将Map对象的键对值都转化为字符后输出
            for (Object obj : ((Map) object).keySet()) {
                map.put(obj.toString(), ((Map) object).get(obj) == null ? "" : ((Map) object).get(obj).toString());
            }
        } else {// 其他情况则用反射输出该对象的所有属性，以属性名（小写）为key，属性值为value
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object obj;
                String value = "";
                try {
                    obj = field.get(object);
                    if (obj == null) {
                        // 属性为空则输出空串
                    } else if (obj instanceof Date) {
                        // 属性为时间类型则输出年月日时分秒格式的字符串
                        value = DateUtil.formatToString((Date) obj,
                                "yyyy-MM-dd HH:mm:ss");
                    } else {
                        // 其他情况下直接转化为字符串
                        value = obj.toString();
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                map.put(field.getName().toLowerCase(), value);
            }
        }
        return map;
    }

    /**
     * 生成EXCEL文件
     *
     * @param data     参数 Map<String,String>对象集合
     * @param filePath 自定义文件路径
     * @param colTitle 匹配的有序列名，可以为空
     * @return File
     * @throws IOException 抛出异常
     * @author 宋展辉 2015年11月6日 下午3:19:58
     */
    public static File getExcelFileFromMap(List<Map<String, String>> data, String filePath,
                                           LinkedHashMap<String, String> colTitle) throws IOException {
        if (data == null || data.size() == 0) {
            return null;
        } else if (StringUtil.isBlank(filePath) || !(filePath.endsWith("xls") || filePath.endsWith("xlsx"))) {
            throw new IOException("传入正确的Excel文件路径");
        } else {
            Workbook wb = null;
            if (filePath.endsWith("xls")) {
                wb = new HSSFWorkbook();
            } else {
                wb = new XSSFWorkbook();
            }
            Sheet sheet = wb.createSheet("sheet1");
            // 先创建表头,将表头内容居中
            Row row = sheet.createRow(0);
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 表头的列值集合
            List<String> keyList = new ArrayList<String>();
            int c = 0;
            // 如果有匹配的列名则完全按照提供的列名键对值进行列填充，反之则用原数据
            if (colTitle != null && colTitle.size() > 0) {
                for (String key : colTitle.keySet()) {
                    keyList.add(key);
                    Cell cell = row.createCell(c++);
                    cell.setCellValue(colTitle.get(key));
                    cell.setCellStyle(style);
                }
            } else {
                for (String key : data.get(0).keySet()) {
                    keyList.add(key);
                    Cell cell = row.createCell(c++);
                    cell.setCellValue(key);
                    cell.setCellStyle(style);
                }
            }
            // 生成各行对应数据
            for (int i = 0; i < data.size(); i++) {
                row = sheet.createRow(i + 1);
                Map<String, String> map = data.get(i);
                for (int j = 0; j < keyList.size(); j++) {
                    row.createCell(j).setCellValue(map.get(keyList.get(j)));
                }
            }
            // 输出Excel文件
            FileOutputStream fos = new FileOutputStream(
                    PathManager.getInstance().getTmpPath().resolve(filePath).toFile());
            wb.write(fos);
            fos.close();
            wb.close();
            return new File(filePath);

        }
    }

    public static File getExcelFileFromList(List<String> dataList, String filePath
    ) throws IOException {

        if (dataList == null || dataList.size() == 0) {
            return null;
        } else if (StringUtil.isBlank(filePath) || !(filePath.endsWith("xls") || filePath.endsWith("xlsx"))) {
            throw new IOException("传入正确的Excel文件路径");
        } else {
            Workbook wb = null;
            if (filePath.endsWith("xls")) {
                wb = new HSSFWorkbook();
            } else {
                wb = new XSSFWorkbook();
            }
            Sheet sheet = wb.createSheet("sheet1");
            // 先创建表头,将表头内容居中
            Row row = sheet.createRow(0);
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 表头的列值集合
            List<String> keyList = new ArrayList<String>();
            int c = 0;
            // 如果有匹配的列名则完全按照提供的列名键对值进行列填充，反之则用原数据

            // 生成各行对应数据
            for (int i = 0; i < dataList.size(); i++) {
                row = sheet.createRow(i + 1);


                row.createCell(0).setCellValue(dataList.get(i));

            }
            // 输出Excel文件
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(
                        PathManager.getInstance().getTmpPath().resolve(filePath).toFile());
                wb.write(fos);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                fos.close();
                wb.close();
            }

            return new File(filePath);

        }
    }

    public static Workbook getExcelBookFromMap(List<Map<String, String>> data,
                                               LinkedHashMap<String, String> colTitle) throws IOException {
        if (data == null || data.size() == 0) {
            return null;
        } else {
            Workbook wb = null;

            wb = new HSSFWorkbook();

            Sheet sheet = wb.createSheet("sheet1");
            // 先创建表头,将表头内容居中
            Row row = sheet.createRow(0);
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 表头的列值集合
            List<String> keyList = new ArrayList<String>();
            int c = 0;
            // 如果有匹配的列名则完全按照提供的列名键对值进行列填充，反之则用原数据
            if (colTitle != null && colTitle.size() > 0) {
                for (String key : colTitle.keySet()) {
                    keyList.add(key);
                    Cell cell = row.createCell(c++);
                    cell.setCellValue(colTitle.get(key));
                    cell.setCellStyle(style);
                }
            } else {
                for (String key : data.get(0).keySet()) {
                    keyList.add(key);
                    Cell cell = row.createCell(c++);
                    cell.setCellValue(key);
                    cell.setCellStyle(style);
                }
            }
            // 生成各行对应数据
            for (int i = 0; i < data.size(); i++) {
                row = sheet.createRow(i + 1);
                Map<String, String> map = data.get(i);
                for (int j = 0; j < keyList.size(); j++) {
                    row.createCell(j).setCellValue(map.get(keyList.get(j)));
                }
            }
            // 输出Excel文件

            return wb;


        }
    }

    /**
     * @param cell 参数
     * @return String
     */
    private static String getKey(Cell cell) {
        String index = "";
        int col = cell.getColumnIndex();
        int row = cell.getRowIndex() + 1;
        int a = 65;
        int m = col % 26;
        int n = col / 26;
        while (n > 0) {
            index = index + 'A';
            n--;
        }
        index = index + (char) (a + m);
        index = index + row;
        return index;
    }

    /**
     * @param input    参数
     * @param fileName 文件名
     * @return Workbook
     * @throws IOException 抛出IO异常
     */
    private static Workbook getWorkBook(InputStream input, String fileName) throws IOException {
        Workbook wb = null;
        if (fileName.indexOf("xlsx") >= 0) {
           wb = new XSSFWorkbook(input);
//            try {
//                wb = WorkbookFactory.create(input);
//            } catch (InvalidFormatException e) {
//                e.printStackTrace();
//            }
        } else if (fileName.indexOf("xls") >= 0) {

            wb = new HSSFWorkbook(input);
        } else {
            throw new IllegalArgumentException("文件类型未知!" + fileName);
        }
        return wb;
    }

    /**
     * @param row 参数
     * @return List
     */
    private static List<String> getHeader(Row row) {
        List<String> cells = new ArrayList<String>();
        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getLastCellNum();
        for (int i = firstCellNum; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            cells.add(getCellValue(cell));
        }
        return cells;
    }

    /**
     * @param cell 参数
     * @return String
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            //CELL_TYPE_NUMERIC 数值型 0
//        CELL_TYPE_STRING 字符串型 1
//        CELL_TYPE_FORMULA 公式型 2
//        CELL_TYPE_BLANK 空值 3
//        CELL_TYPE_BOOLEAN 布尔型 4
//        CELL_TYPE_ERROR 错误 5
            case Cell.CELL_TYPE_STRING:
                try {
                    String value = cell.getStringCellValue();
                    String temp = new String(Hex.encodeHex(value.getBytes("utf-8"))).replaceAll("c2a0", "20");
                    try {
                        value = new String(Hex.decodeHex(temp.toCharArray()), "utf-8");
                    } catch (Exception localException1) {
                    }
                    return value.trim();
                } catch (UnsupportedEncodingException e1) {
                    return new String(cell.getStringCellValue().getBytes());
                }
            case Cell.CELL_TYPE_NUMERIC:
               /* if ((DateUtil.checkDate(cell.getStringCellValue(),"YYYY/mm/dd")) || ((cell.getCellStyle() != null)
                        && ("yyyy\"年\"m\"月\";@".equals(cell.getCellStyle().getDataFormatString())))) {
                    String format = cell.getCellStyle().getDataFormatString();
                    if (format.equals("yyyy\"年\"m\"月\";@")) {
                        format = "yyyy-MM";
                    } else if (format.startsWith("yyyy\\-mm\\-dd")) {
                        format = format.replace("yyyy\\-mm\\-dd", "yyyy-MM-dd");
                    } else if (format.startsWith("m/d/yy")) {
                        format = format.replace("m/d/yy", "yyyy/MM/dd");
                    } else if (format.startsWith("[$-F800]dddd\\,\\ mmmm\\ dd\\,\\ yyyy")) {
                        format = format.replace("[$-F800]dddd\\,\\ mmmm\\ dd\\,\\ yyyy", "yyyy年MM月dd日");
                    } else if (format.startsWith("yyyy\\-m\\-d")) {
                        format = format.replace("yyyy\\-m\\-d", "yyyy-MM-dd");
                    } else {
                        format = "yyyy-MM-dd HH:mm:ss";
                    }
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        return sdf.format(cell.getDateCellValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return cell.getDateCellValue().toString();
                    }
                }*/
                NumberFormat nf = NumberFormat.getInstance(Locale.CHINESE);
                return String.valueOf(nf.format(cell.getNumericCellValue()).replace(",", ""));
            case 4:
                return String.valueOf(cell.getBooleanCellValue());
            case 2:
                return String.valueOf(cell.getCellFormula());
            default:
                return "";
        }
    }

    /**
     * @param args 参数
     * @throws IOException 抛出IO异常
     */
    public static void main1(String[] args) throws IOException {
        /*
		 * List<Map<String, String>> list = new ArrayList<Map<String,
		 * String>>(); for (int i = 0; i < 10; i++) { Map<String, String> map =
		 * new HashMap<String, String>(); map.put("a", "aaaa"); map.put("b",
		 * "bbbb"); map.put("c", "cccc"); map.put("d", "dddd"); list.add(map); }
		 * LinkedHashMap<String, String> colTitle = new LinkedHashMap<String,
		 * String>(); colTitle.put("a", "第一列"); colTitle.put("c", "第二列");
		 * colTitle.put("d", "第三列"); getExcelFile(list, "E:\\1.xlsx", colTitle);
		 */

        List<Map<String, String>> listMap = ExcelUtil.getExcelData(new File("C:\\zzw\\doc\\2016-08-03\\台州所有酒店.xlsx"));

        for (int i = 0; i < listMap.size(); i++) {
            Map<String, String> map = listMap.get(i);
            System.out.println("," + map.get("a"));
        }

    }

//
//    public static void main(String args[]){
//        ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
//        List<Map<String,String>> dataList =new ArrayList<Map<String,String>>();
//
//        Map<String,String> map =new HashMap<String ,String>();
//        map.put("a","1");
//
//        dataList.add(map);
//        LinkedHashMap titleMap =new LinkedHashMap();
//        titleMap.put("a","你好");
//        try {
//            ExcelUtil.getExcelBookFromMap(dataList,titleMap).write(outputStream);
//            InputStream is =new StreamUtil().parse(outputStream);
//            MockMultipartFile file123 = new MockMultipartFile("file","1.gif","image/jpeg",is);
//            MockMultipartHttpServletRequest request123 = new MockMultipartHttpServletRequest() ;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    /**
     * 根据文件获得sheet
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Sheet getExcelSheetFromFile(File file) throws IOException {
        FileInputStream inputStream = null;

        inputStream = new FileInputStream(file);
        return getExcelSheetFromInputStream(inputStream);

    }

    /**
     * 从文件流中获取sheet
     * 此方法最终关闭流
     *
     * @param stream
     * @return
     * @throws IOException
     */
    public static Sheet getExcelSheetFromInputStream(InputStream stream) throws IOException {
        try {
            Workbook wb = getWorkBook(stream, "excel.xls");
            Sheet sheet = wb.getSheetAt(0);
            return sheet;
        } finally {
            stream.close();
        }
    }

    /**
     * 文件解析器.获取sheet(仅适用于单个文件)
     *
     * @param sheet
     * @return List<Map <Integer ,String>>
     * @throws Exception 异常
     * @author zhangzhiwei
     * @date 2017年2月13日 上午9:49:54
     */
    public static List<Map<Integer, String>> getListFromSheet(Sheet sheet) throws Exception {

        List<Map<Integer, String>> retDats = new ArrayList<Map<Integer, String>>();
        int firsRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        Row headerRow = sheet.getRow(firsRowNum);
        if (headerRow == null) {
            throw new IOException("excel头部数据缺失 请按模板使用");
        }
        List<String> header = getHeader(headerRow);

        for (int i = firsRowNum + 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Map<Integer, String> eRow = new LinkedHashMap<Integer, String>();//以列号为key
            for (int j = 0; j < header.size(); j++) {
                Cell cell = row.getCell(j);

                String value = getCellValue(cell);
                eRow.put(j, value);
            }
            retDats.add(eRow);
        }
        return retDats;
    }

    /**
     * 文件解析器.获取sheet(仅适用于单个文件)
     *
     * @param sheet excelsheet
     * @param keys  列名
     * @return Sheet
     * @throws Exception 异常
     * @author zhangzhiwei
     * @date 2017年2月13日 上午9:49:54
     */
    public static List<Map<String, String>> getListFromSheet(Sheet sheet, List<String> keys) throws Exception {

        List<Map<String, String>> retDats = new ArrayList<Map<String, String>>();
        int firsRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        Row headerRow = sheet.getRow(firsRowNum);
        if (headerRow == null) {
            throw new IOException("excel头部数据缺失 请按模板使用");
        }

        for (int i = firsRowNum + 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Map<String, String> eRow = new LinkedHashMap<String, String>();//以列号为key
            for (int j = 0; j < keys.size(); j++) {
                Cell cell = row.getCell(j);

                String value = getCellValue(cell);
                eRow.put(keys.get(j), value);
            }
            retDats.add(eRow);
        }
        return retDats;
    }
}