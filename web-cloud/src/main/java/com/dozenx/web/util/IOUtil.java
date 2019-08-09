package com.dozenx.web.util;

import com.dozenx.common.util.FileUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月20日 上午10:26:37
 * 创建作者：亢燕翔
 * 文件名称：IOUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class IOUtil {

    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    /**
     * 下载工具类
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param response 响应
     * @author 亢燕翔
     * @throws IOException
     * @date 2017年1月20日 上午10:26:56
     */
    public static void download(String fileName, String filePath, HttpServletResponse response) {
        OutputStream os = null;
        InputStream inputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            inputStream = new FileInputStream(new File(filePath + File.separator + fileName));
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            logger.error("",e);
        } finally {
            close(os);//关闭流，释放资源
            close(inputStream);//关闭流，释放资源
            /*删除临时文件*/
            if(filePath.substring(filePath.length()-4, filePath.length()).equals("temp")){
                delLocalFile(filePath + File.separator + fileName);
            }
        }
    }

    /**
     * 删除本地文件
     * @param srcFile 本地文件路径
     * @author 亢燕翔
     * @date 2017年2月3日 上午11:11:15
     */
    private static void delLocalFile(String srcFile) {
        File file = new File(srcFile);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 保存图片
     * @param multipartFile 文件
     * @param path 图片保存路径
     * @author 周颖
     * @throws Exception 异常
     * @date 2017年4月13日 上午11:05:58
     */
    public static void savePicture(MultipartFile multipartFile,String path) throws Exception {
        logger.debug("提示：图片路径= " + path);
        //创建对应的文件夹，防止文件不存在时报错
        mkDirsByFilePath(path);
        multipartFile.transferTo(new File(path));
    }



    /**
     * 创建文件夹
     * @param filePath 文件路径
     * @author 周颖
     * @date 2017年4月13日 下午2:19:38
     */
    public static void mkDirsByFilePath(String filePath){
        if(StringUtil.isBlank(filePath)){
            return;
        }
        int lastIndex = filePath.lastIndexOf("/");
        if(lastIndex == -1){
            lastIndex = filePath.lastIndexOf("\\");
        }
        if(lastIndex == -1){
            return;
        }
        String folderPath = filePath.substring(0, lastIndex);
        logger.debug("提示：文件所对应的文件夹路径[folderPath]= " + folderPath);
        mkDirs(folderPath);
    }



    /**
     * 创建文件夹
     * @param path 路径
     * @author 周颖
     * @date 2017年4月13日 上午10:28:19
     */
    public static void mkDirs(String path) {
        if (StringUtil.isBlank(path)) {
            logger.debug("错误：path 为空！");
            return;
        }
        File file = new File(path);
        boolean isDirectory = file.exists() && file.isDirectory();
        if (isDirectory) {// 当存在时，自动跳过
            logger.debug("提示：路径(" + path + ") 已存在！");
        } else {// 当不存在时，自动创建该目录
            boolean executeResult = file.mkdirs();
            if (executeResult) {
                logger.debug("提示：文件夹(" + path + ") 创建成功！");
            } else {
                logger.debug("提示：文件夹(" + path + ") 创建失败！");
            }
        }
    }

    /**
     * 复制文件
     * @param is 文件流
     * @param to 目标路径
     * @return 文件大小
     * @author 周颖
     * @date 2017年4月13日 上午11:09:24
     */
    public static Map<String,Object> copyFile(InputStream is, String to) {
        Map<String,Object> params = new HashMap<String,Object>();
        if (is == null) {
            params.put("fileSize", 0);
            return params;
        }
        int fileSize = 0;
        OutputStream os = null;
        try {
            os = new FileOutputStream(to);
            int len;
            byte[] bytes = new byte[1024];
            while ((len= is.read(bytes))!=0){
                os.write(bytes,0,len);
                fileSize+=len;
            }
          //  fileSize = IOUtils.copy(is, os);
            logger.debug("提示：文件大小= " + fileSize);
        } catch (Exception e) {
             logger.error("",e);
        } finally {
            close(is);//关闭流，释放资源
            close(os);//关闭流，释放资源
        }
        params.put("fileSize", fileSize);
        return params;
    }

    /**
     * 移除文件或目录
     * @param path 路径
     * @author 周颖
     * @throws Exception
     * @date 2017年4月13日 下午2:17:13
     */
    public static void remove(String path) throws Exception {
        if (StringUtil.isBlank(path)) {
            logger.debug("提示：需要删除文件的【path】为空！");
            return;
        }
        File file = null;
        file = new File(path);
        if (file.isDirectory()) {// 目录
            logger.debug("提示：开始删除目录{ " + path + " }");
            FileUtil.deleteDir(file);
        } else if (file.isFile()) {// 文件
            logger.debug("提示：开始删除文件{ " + path + " }");
            file.delete();
        }
    }

    /**
     * 移除文件或目录
     * @param file 文件
     * @author 周颖
     * @throws Exception
     * @date 2017年4月13日 下午2:15:03
     */
    public static void remove(File file) throws Exception {
        if (file.isDirectory()) {// 目录
            FileUtil.deleteDir(file);
        } else if (file.isFile()) {// 文件
            file.delete();
        }
    }

    /**
     * 替换文件内文本
     * @param optFile 要处理的文件
     * @param originStr 原文本
     * @param replaceStr 替代的文本
     * @author 周颖
     * @date 2017年4月13日 下午2:14:20
     */
    public static void replaceText(File optFile, String originStr, String replaceStr) {
        if(!optFile.exists()){//文件不存在
            return;
        }
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(optFile)));
            String filecontent = null;
            List<String> fileContentList = new ArrayList<String>();
            while ((filecontent = br.readLine()) != null) {
                fileContentList.add(filecontent);
            }
            br.close();
            pw = new PrintWriter(new FileOutputStream(optFile));
            // 输出替换完成的文件
            int maxSize = fileContentList.size();
            String lineSeparator = null;// 换行符
            if (maxSize > 1) {
                lineSeparator = System.getProperty("line.separator");
            }
            for (int i = 0; i < maxSize; i++) {
                pw.write(fileContentList.get(i).replaceAll(originStr, replaceStr));
                if (i < (maxSize - 1)) {
                    pw.write(lineSeparator);
                }
            }
            pw.flush();
        } catch (IOException e) {
            logger.error("",e);
        } finally {
            close(pw);//关闭流，释放资源
        }
    }

    /**
     * 文件复制
     * @param src 原路径
     * @param target 目的路径
     * @author 周颖
     * @date 2017年4月24日 下午2:07:38
     */
    public static void fileChannelCopy(String src, String target) {
        File s = new File(src);
        File t = new File(target);
        if (!t.exists()) {
            mkDirsByFilePath(target);
        }
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
             logger.error("",e);
        } finally {
            close(fi);//关闭流，释放资源
            close(in);//关闭流，释放资源
            close(fo);//关闭流，释放资源
            close(out);//关闭流，释放资源
        }
    }

    /**
     * 文件合并
     * @param outFile 输出文件的路径
     * @param files 多个来源文件路径
     * @author 周颖
     * @date 2017年4月24日 下午2:07:38
     */
    public static void mergeFiles(String outFile, String[] files) {
        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        try{
            //缓冲System.in输入流
            //System.in是位流，可以通过InputStreamReader将其转换为字符流
            //缓冲FileWriter
            bufWriter = new BufferedWriter(new FileWriter(outFile, true));
            String input = null;
            for(int i=0; i<files.length; i++){
                if(files[i].equals(outFile)){
                    continue;
                }
                bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(files[i])));
                //每读一行进行一次写入动作
                input = bufReader.readLine();
                while(input != null){
                    bufWriter.write(input);
                    //newLine()方法写入与操作系统相依的换行字符，依执行环境当时的OS来决定该输出那种换行字符
                    //bufWriter.newLine();
                    input = bufReader.readLine();
                }
                bufReader.close();
            }
            bufWriter.flush();
        }catch(Exception e){
             logger.error("",e);
        }finally{
            close(bufWriter);//关闭流，释放资源
        }
    }

    /**
     * 文件夹复制
     * @param sourceDir 源文件夹
     * @param targetDir 目的文件夹
     * @author 周颖
     * @date 2017年4月24日 下午5:29:08
     */
    public static void copyDirectiory(String sourceDir, String targetDir) {
        try {
            if (!new File(sourceDir).isDirectory()) {
                return;
            }
            // 新建目标目录
            (new File(targetDir)).mkdirs();
            // 获取源文件夹当前下的文件或目录
            File[] file = (new File(sourceDir)).listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    // 源文件
                    File sourceFile = file[i];
                    // 目标文件
                    File targetFile = new File(new File(targetDir).getAbsolutePath() + "/" + file[i].getName());
                    fileChannelCopy(sourceFile.getAbsolutePath(), targetFile.getAbsolutePath());
                }
                if (file[i].isDirectory()) {
                    // 准备复制的源文件夹
                    String dir1 = sourceDir + "/" + file[i].getName();
                    // 准备复制的目标文件夹
                    String dir2 = targetDir + "/" + file[i].getName();
                    copyDirectiory(dir1, dir2);
                }
            }
        } catch (Exception e) {
             logger.error("",e);
        }
    }

    /**
     * 文件内容追加
     * @param fileName 文件名
     * @param content 内容
     * @author 周颖
     * @date 2017年4月24日 下午6:49:29
     */
    public static void fileAppendContent(String fileName, String content) {
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
             logger.error("",e);
        } finally {
            close(writer);//关闭流，释放资源
        }
    }

    /**
     * 文件内容替换
     * @param filePath 文件路径
     * @param src 原文本
     * @param target 要替换的文本
     * @author 周颖
     * @date 2017年5月16日 下午2:41:57
     */
    public static void replaceContentToFile(String filePath, String src, String target) {
        FileReader read = null;
        BufferedReader br = null;
        StringBuilder content = null;
        FileOutputStream fs = null;
        try {
            read = new FileReader(filePath);
            br = new BufferedReader(read);
            content = new StringBuilder();
            int dex=0;
            while (br.ready()) {
                content.append(br.readLine());
                while(dex != -1){
                    dex = content.indexOf(src,dex+1);
                    if (dex != -1) {
                        content.delete(dex, src.length() + dex);
                        content.insert(dex, target);
                    }
                }
                //content.append("\n");
            }

            br.close();
            read.close();
            fs = new FileOutputStream(filePath);
            fs.write(content.toString().getBytes());
            fs.close();

        } catch (FileNotFoundException e) {
            logger.error("",e);


        } catch (IOException e) {
            logger.error("",e);

        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (read != null) {
                    read.close();
                }
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {

            }
        }
    }

    /**
     * 文件转化byte
     * @param filePath 文件路径
     * @return 二进制
     * @author 周颖
     * @throws Exception
     * @date 2017年5月19日 下午1:46:08
     */
    public static byte[] fileToByte(String filePath) throws Exception{
        byte[] buffer = null;
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1){
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        return buffer;
    }

    /**
     * 从request获取文件
     * @param request 前端请求
     * @return MultipartFile 文件信息
     * @author 伍恰
     * @date 2017年6月27日 下午7:45:03
     */
    public static MultipartFile getFileFromRequest(HttpServletRequest request){
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartFile file = null;
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;//转换成多个请求
            Iterator<String>  iter = multiRequest.getFileNames();
            while(iter.hasNext()){//遍历文件
                file = multiRequest.getFile((String)iter.next());
                if(file == null){//文件为空跳过
                    continue;
                }
            }
        }
        return file;
    }

    /**
     * 将is流转成字符串
     * @param is InputStream
     * @return 字符串
     * @throws Exception 异常
     * @author 许小满
     * @date 2017年9月4日 下午7:15:24
     */
    public static String ioToString(InputStream is) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while(-1 != (len = is.read(buffer))){
            baos.write(buffer,0,len);
            baos.flush();
        }
        return baos.toString("utf-8");
    }

    /**
     * 将inputstream流转成byteBuffer
     * @param is io流
     * @return byteBuffer
     * @throws Exception 异常
     * @author 许小满
     * @date 2017年9月6日 上午8:50:56
     */
    public static ByteBuffer ioToByteBuffer(InputStream is) throws Exception{
        if(is == null){
            return null;
        }
        ByteArrayOutputStream bos = null;
        ByteBuffer byteBuffer = null;
        try{
            bos = new ByteArrayOutputStream();
            byte[] byts = new byte[1024];
            int len = 0;
            while ((len = is.read(byts)) >= 0){
                bos.write(byts, 0, len);
            }
            byteBuffer = ByteBuffer.wrap(bos.toByteArray());
        }catch(Exception e){
            throw e;
        }finally{
            close(is);
            close(bos);
        }
        return byteBuffer;
    }

    /**
     * 返回错误的json格式的信息 对接第三方 保持旧的出参
     * @param response response对象
     * @param errorMessage 错误信息
     * @author 许小满
     * @date 2015年11月30日 下午1:42:10
     */
    public static void responseError(HttpServletResponse response, String errorMessage) {
        Map<String, Object> resultMap = new HashMap<String, Object>(2);
        resultMap.put("result", "FAIL");
        resultMap.put("message", errorMessage);
        String json = JsonUtil.toJson(resultMap);
        PrintWriter out = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            out = response.getWriter();
            out.write(json);
            out.flush();
        } catch (IOException e) {
            logger.error("",e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 关闭流，释放资源
     * @param is io流
     * @author 许小满
     * @date 2017年9月4日 下午11:17:18
     */
    public static void close(InputStream is){
        if(is == null){
            return;
        }
        try {
            is.close();
        } catch (IOException e) {}
    }

    /**
     * 关闭流，释放资源
     * @param os io流
     * @author 许小满
     * @date 2017年9月4日 下午11:17:18
     */
    public static void close(OutputStream os){
        if(os == null){
            return;
        }
        try {
            os.close();
        } catch (IOException e) {}
    }

    /**
     * 关闭流，释放资源
     * @param c io流
     * @author 许小满
     * @date 2017年9月4日 下午11:17:18
     */
    public static void close(Channel c){
        if(c == null){
            return;
        }
        try {
            c.close();
        } catch (Exception e) {}
    }

    /**
     * 关闭流，释放资源
     * @param w io流
     * @author 许小满
     * @date 2017年9月4日 下午11:17:18
     */
    public static void close(Writer w){
        if(w == null){
            return;
        }
        try {
            w.close();
        } catch (Exception e) {}
    }

    /**
     * 关闭流，释放资源
     * @param r io流
     * @author 许小满
     * @date 2017年9月26日 上午8:54:33
     */
    public static void close(Reader r){
        if(r == null){
            return;
        }
        try {
            r.close();
        } catch (Exception e) {}
    }
}
