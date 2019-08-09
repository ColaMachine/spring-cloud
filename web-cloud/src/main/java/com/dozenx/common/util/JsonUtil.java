/**
 *
 */
package com.dozenx.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

//import org.apache.commons.lang3.StringUtils;

//import org.apache.commons.lang3.StringUtils;

/**
 * json序列化及反序列化工具类。
 *
 * @author awifi-core
 * @date 2015年1月7日 上午11:47:42
 */
public class JsonUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * json字符串转化为list 还可以 直接使用 JsonUtils.getInstance().readValue(String content,
     * new TypeReference<List<T>>(){})方式
     *
     * @param <T>
     * @param content
     * @return
     * @throws IOException
     */
	/*
	 * public static <T> List<T> toJavaBeanList123(String content,
	 * TypeReference<List<T>> typeReference) throws IOException {
	 *
	 * try {
	 *
	 * return objectMapper.readValue(content, typeReference); } catch
	 * (JsonParseException e) { logger.error("json字符串转化为 list失败,原因:" +
	 * e.toString()); throw new RuntimeException("json字符串转化为 list失败"); } catch
	 * (JsonMappingException e) { logger.error("json字符串转化为 list失败,原因" +
	 * e.toString()); throw new JsonMappingException("json字符串转化为 list失败"); }
	 * catch (IOException e) { logger.error("json字符串转化为 list失败,原因" +
	 * e.toString()); throw new IOException("json字符串转化为 list失败"); } }
	 */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    /**
     * 使用Jackson 数据绑定 将对象转换为 json字符串 还可以 直接使用
     * JsonUtils.getInstance().writeValueAsString(Object obj)方式
     *
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * json字符串转化为 JavaBean 还可以直接JsonUtils.getInstance().readValue(String
     * content,Class valueType)用这种方式
     *
     * @param <T>
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T toJavaBean(String content, Class<T> valueType) {

        return JSON.parseObject(content, valueType);
    }

    public static <T> List<T> toList(String content, Class<T> valueType) throws IOException {
        return JSON.parseArray(content, valueType);
        /*
		 * try {
		 *
		 * return objectMapper.readValue(content, typeReference); } catch
		 * (JsonParseException e) { logger.error("json字符串转化为 list失败,原因:" +
		 * e.toString()); throw new RuntimeException("json字符串转化为 list失败"); }
		 * catch (JsonMappingException e) { logger.error("json字符串转化为 list失败,原因"
		 * + e.toString()); throw new JsonMappingException("json字符串转化为 list失败");
		 * } catch (IOException e) { logger.error("json字符串转化为 list失败,原因" +
		 * e.toString()); throw new IOException("json字符串转化为 list失败"); }
		 */
    }

    /**
     * 将一个参数Map转换成JSON字符串
     */
    public static String toJsonStringByMap(Map<String, List<String>> param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder("{");

        for (Entry<String, List<String>> p : param.entrySet()) {
            String key = p.getKey();
            List<String> vals = p.getValue();

            if (key.equals("msgId")) {
                sb.append("\"msgId\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("sender")) {
                sb.append("\"sender\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("createTime")) {
                sb.append("\"createTime\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("liveTime")) {
                sb.append("\"liveTime\":").append(converter(Integer.valueOf(vals.get(0)))).append(",");
            } else if (key.equals("receivers")) {
                sb.append("\"receivers\":").append(converter(vals)).append(",");
            } else if (key.equals("stationCodes")) {
                sb.append("\"stationCodes\":").append(converter(vals)).append(",");
            } else if (key.equals("StationTypes")) {
                sb.append("\"stationTypes\":").append(converter(vals)).append(",");
            } else if (key.equals("blackList")) {
                sb.append("\"blackList\":").append(converter(vals)).append(",");
            } else if (key.equals("blackListType")) {
                sb.append("\"blackListType\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("msgType")) {
                sb.append("\"msgType\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("format")) {
                sb.append("\"format\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("title")) {
                sb.append("\"title\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("content")) {
                sb.append("\"content\":").append(converter(vals.get(0))).append(",");
            } else if (key.equals("saveOffline")) {
                sb.append("\"saveOffline\":").append(converter(vals.get(0))).append(",");
            }
        }

        int lastCommaIndex = sb.lastIndexOf(",");
        sb.replace(lastCommaIndex, lastCommaIndex + 1, "");

        sb.append("}");

        return sb.toString();
    }

    /**
     * 转换器
     */
    private static String converter(String value) {

        return value == null ? "null" : ("\"" + value + "\"");
    }

    private static String converter(int value) {

        return String.valueOf(value);
    }

    private static String converter(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "null";
        }

        StringBuilder sb = new StringBuilder("[");

        for (int i = 0, len = values.size(); i < len; i++) {
            sb.append(converter(values.get(i)));

            if (i < len - 1) {
                sb.append(",");
            }
        }
        sb.append("]");

        return sb.toString();
    }

    private static String converter(boolean value) {

        return String.valueOf(value);
    }

    /**
     * 遍历一个对象的所有属性
     *
     * @param clazz
     * @param list
     */
    public static void getAllFields(Class clazz, List<Field> list) {
        for (Field f : clazz.getDeclaredFields()) {
            list.add(f);
        }
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getAllFields(superClazz, list);
        }
    }

    /**
     * object对象转json
     *
     * @param model
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static String getJson(Object model)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Field[] field = model.getClass().getDeclaredFields();// 获取实体类的所有属性，返回Field数组
        StringBuffer buffer = new StringBuffer("");
        for (int i = 0; i < field.length; i++) {// 遍历所有属性
            field[i].setAccessible(true);
            String name = field[i].getName();// 获取属性的名字
            if (buffer.toString().equals("")) {
                buffer.append("\"" + name + "\":");
            } else {
                buffer.append(",\"" + name + "\":");
            }
            name = name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写，方便构造get，set方法
            String type = field[i].getGenericType().toString();// 获取属性的类型

            Method m = null;
            try {
                m = model.getClass().getMethod("get" + name);
            } catch (Exception e) {
                if (e instanceof NoSuchMethodException) {
                    continue;
                }
            }

            if (m != null) {
                m.setAccessible(true);
                if (type.equals("class java.lang.String")) {// 如果type是类类型，则前面包含"class
                    // "，后面跟类名
                    String value = (String) m.invoke(model);// 调用getter方法获取属性值
                    if (value != null) {
                        buffer.append("\"" + value + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                }
                if (type.equals("class java.lang.Integer")) {
                    Integer value = (Integer) m.invoke(model);
                    if (value != null) {
                        buffer.append("\"" + value + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                }
                if (type.equals("class java.lang.Long")) {
                    Integer value = (Integer) m.invoke(model);
                    if (value != null) {
                        buffer.append("\"" + value + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                }
                if (type.equals("class java.lang.Short")) {
                    Short value = (Short) m.invoke(model);
                    if (value != null) {
                        buffer.append("\"" + value + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                }
                if (type.equals("class java.lang.Double")) {
                    Double value = (Double) m.invoke(model);
                    if (value != null) {
                        buffer.append("\"" + value + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                }
                if (type.equals("class java.lang.Boolean")) {
                    Boolean value = (Boolean) m.invoke(model);
                    if (value != null) {
                        buffer.append("" + value + "");
                    } else {
                        buffer.append(Boolean.FALSE.toString());
                    }
                }
                if (type.equals("class java.util.Date")) {
                    Date value = (Date) m.invoke(model);
                    if (value != null) {
                        buffer.append("\"" + new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(value) + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                }
            }

        }
        return buffer.toString();
    }

    /**
     * parse object（include superclass）to json string
     *
     * @param model
     * @return
     * @throws Exception
     */
    public static String objectToJsonStr(Object model) throws Exception {
        // 获取实体类的所有属性，返回Field数组
        List<Field> fields = new ArrayList<Field>();
        getAllFields(model.getClass(), fields);
        StringBuffer buffer = new StringBuffer("{");
        Field field = null;
        for (int i = 0; i < fields.size(); i++) {// 遍历所有属性
            field = fields.get(i);
            field.setAccessible(true);
            String name = field.getName();// 获取属性的名字
            if (i > 0) {
                buffer.append(",");
            }
            buffer.append("\"" + name + "\":");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写，方便构造get，set方法
            String type = field.getGenericType().toString();// 获取属性的类型

            Method m = null;
            try {
                m = model.getClass().getMethod("get" + name);
            } catch (Exception e) {
                if (e instanceof NoSuchMethodException) {
                    continue;
                }
            }
            if (m != null) {
                m.setAccessible(true);
                if (type.equals("class java.lang.String") || type.equals("class java.lang.Integer")
                        || type.equals("class java.lang.Long") || type.equals("class java.lang.Short")
                        || type.equals("class java.lang.Float") || type.equals("class java.lang.Double")
                        || type.equals("class java.lang.Byte")) { // 如果type是类类型，则前面包含"class
                    // "，后面跟类名
                    Object value = m.invoke(model);// 调用getter方法获取属性值
                    if (value != null) {
                        buffer.append("\"" + String.valueOf(value) + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                } else if (type.equals("class java.lang.Boolean")) {
                    Boolean value = (Boolean) m.invoke(model);
                    if (value != null) {
                        buffer.append(value);
                    } else {
                        buffer.append(false);
                    }
                } else if (type.equals("class java.util.Date")) {
                    Date value = (Date) m.invoke(model);
                    if (value != null) {
                        buffer.append("\"" + new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(value) + "\"");
                    } else {
                        buffer.append("\"\"");
                    }
                } else if (type.equals("interface java.util.List")) {
                    List<Object> value = (List<Object>) m.invoke(model);
                    buffer.append(listToJson(value));
                } else if (type.equals("class [Ljava.lang.String;")) {// 字符串数组类型
                    String[] value = (String[]) m.invoke(model);
                    StringBuffer valueStr = new StringBuffer("");
                    buffer.append("[");
                    if (value != null) {
                        for (String str : value) {
                            if (valueStr.toString().equals("")) {
                                valueStr.append("\"" + str + "\"");
                            } else {
                                valueStr.append(",\"" + str + "\"");
                            }
                        }
                        buffer.append(valueStr.toString());
                    }
                    buffer.append("]");
                }
            }

        }
        buffer.append("}");
        return buffer.toString();
    }

    /**
     * parse list to json string
     *
     * @param list
     * @return
     */
    public static String listToJsonStr(List<Object> list) {
        StringBuffer buffer = new StringBuffer("[");
        try {
            if (list != null && list.size() > 0) {
                for (Object o : list) {
                    if (buffer.toString().length() > 1) {
                        buffer.append(",");
                    }
                    buffer.append(objectToJson(o));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * parse object（include superclass）to json Object
     *
     * @param model
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToJson(Object model) throws Exception {
        // 获取实体类的所有属性，返回Field数组
        List<Field> fields = new ArrayList<Field>();
        getAllFields(model.getClass(), fields);
        Map<String, Object> map = new HashMap<String, Object>();
        Field field = null;
        for (int i = 0; i < fields.size(); i++) {// 遍历所有属性
            field = fields.get(i);
            field.setAccessible(true);
            String name = field.getName();// 获取属性的名字
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写，方便构造get，set方法
            String type = field.getGenericType().toString();// 获取属性的类型
            Method m = null;
            try {
                m = model.getClass().getMethod(methodName);
            } catch (Exception e) {
                if (e instanceof NoSuchMethodException) {
                    continue;
                }
            }
            if (m != null) {
                m.setAccessible(true);
                Object parseValue = "";
                if (m.invoke(model) != null) {
                    if (type.equals("class java.lang.String") || type.equals("class java.lang.Integer")
                            || type.equals("class java.lang.Long") || type.equals("class java.lang.Short")
                            || type.equals("class java.lang.Float") || type.equals("class java.lang.Double")
                            || type.equals("class java.lang.Byte")) { // 如果type是类类型，则前面包含"class
                        // "，后面跟类名
                        Object value = m.invoke(model);// 调用getter方法获取属性值
                        parseValue = value == null ? "" : String.valueOf(value);
                    } else if (type.equals("class java.lang.Boolean")) {
                        Boolean value = (Boolean) m.invoke(model);
                        parseValue = value == null ? false : value;
                    } else if (type.equals("class java.util.Date")) {
                        Date value = (Date) m.invoke(model);
                        parseValue = value == null ? "" : new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(value);
                    } else if (type.equals("interface java.util.List")) {
                        List<Object> value = (List<Object>) m.invoke(model);
                        parseValue = listToJson(value);
                    } else if (type.equalsIgnoreCase("class [Ljava.lang.String;")) {// 字符串数组类型
                        String[] value = (String[]) m.invoke(model);
                        parseValue = value;
                    }
                    map.put(name, parseValue);
                }
            }
        }
        return map;
    }

    /**
     * parse list to json Object
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> listToJson(List<Object> list) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (Object o : list) {
                resultList.add(objectToJson(o));
            }
        }

        return resultList;
    }

    public static List<Map<String, Object>> arrayToJson(Object[] array) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (array != null && array.length > 0) {
            for (Object o : array) {
                resultList.add(objectToJson(o));
            }
        }

        return resultList;
    }

    @SuppressWarnings("unchecked")
    public static void Reflect_Object(Object o, String classPath) {
        try {
            Class _class = Class.forName(classPath);// 加载类
            recursive(o, _class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归遍历类及父类的属性值
     *
     * @param o
     * @param _class
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class recursive(Object o, Class _class) {
        if (_class == null) {
            return null;
        } else {
            Method[] methods = _class.getDeclaredMethods();// 获得类的方法集合
            // 遍历方法集合
            for (int i = 0; i < methods.length; i++) {
                // 获取所有getXX()的返回值
                if (methods[i].getName().startsWith("get")) {// 方法返回方法名
                    methods[i].setAccessible(true);// 允许private被访问(以避免private
                    // getXX())
                    Object object;
                    try {
                        object = methods[i].invoke(o, null);
                        System.out.println(" " + methods[i].getName() + "=" + object);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            return recursive(o, _class.getSuperclass());
        }
    }

    public static String convertDateToSafeString(Date dt) {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(dt);
    }

    public static String convertToSafeString(String str) {
        return str == null || str.equalsIgnoreCase("null") ? "" : str;
    }
	/*
	 *
	 * public static void main(String[] args) { AccountWithBLOBs account = new
	 * AccountWithBLOBs(); String[] tags = {"123","456"}; account.setTags(tags);
	 * account.setCreateDatetime(new Date());
	 *
	 * try { System.out.println(objectToJson(account)); } catch
	 * (NoSuchMethodException e) { e.printStackTrace(); } catch
	 * (IllegalAccessException e) { e.printStackTrace(); } catch
	 * (IllegalArgumentException e) { e.printStackTrace(); } catch
	 * (InvocationTargetException e) { e.printStackTrace(); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 */

    public static JSONObject toJsonObject(String str) {
        return JSON.parseObject(str);
    }


    /**
     * 将对象转为JSON字符转
     *
     * @param obj 数据源
     * @return json字符串
     * @author 亢燕翔
     * @date Jan 9, 2017 8:09:25 PM
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return StringUtil.EMPTY;
        }
        return JSON.toJSONString(obj);
    }

    /**
     * 将json转为指定对象
     *
     * @param <T>
     * @param json    json数据
     * @param typeOfT 数据类型
     * @return new 数据
     * @author 亢燕翔
     * @date Jan 9, 2017 8:14:29 PM
     */
    public static <T> T fromJson(String json, Class<T> typeOfT) {
        try {
            return JSON.parseObject(json, typeOfT);
        } catch (Exception e) {//当报错时，输出error级别的日志，便于定位问题
            logger.error(json);
            throw e;
        }
    }


    public static void main(String args[]) {
        JSONObject obj = JSON.parseObject("{'rex':'(-|+)?(90.0{0,6}|(\\\\d|[1-8]\\\\d).\\\\d{0,6})'}");
        System.out.print("allright");
    }


    public static Long[] convertDigitAryToLongAry(ArrayList<Number> ary) {
        Long[] newAry = new Long[ary.size()];
        for (int i = 0; i < ary.size(); i++) {
            newAry[i] = ary.get(i).longValue();
        }
        return newAry;
    }

    /**
     * 将json数组转化为Long型
     *
     * @param str
     * @return
     */
    public static Long[] getJsonToLongArray(String str) {
        JSONArray jsonArray = JSON.parseArray(str);
        Long[] arr = new Long[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            arr[i] = jsonArray.getLong(i);
            System.out.println(arr[i]);
        }
        return arr;
    }

    public static Map<String,Object> toMap (String str){
       return  toJavaBean(str,HashMap.class);
    }
}
