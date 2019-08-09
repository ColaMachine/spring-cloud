package com.dozenx.web.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpPostUtil {
    URL url;  
    HttpURLConnection conn;  
    String boundary = "--------------------56423498738365";  //-----------httpok
    Map<String, String> textParams = new HashMap<String, String>();  
    Map<String, File> fileparams = new HashMap<String, File>();  
    Map<String, CommonsMultipartFile> cmfileparams = new HashMap<String, CommonsMultipartFile>();  
    
    DataOutputStream ds;  
  
    public HttpPostUtil(String url) throws Exception {  
        this.url = new URL(url);  
    }  
    /**
     * 重新设置要请求的服务器地址，即上传文件的地址。  
     * @param url
     * @throws Exception
     * @author dozen.zhang
     */
    public void setUrl(String url) throws Exception {  
        this.url = new URL(url);  
    }  
  
    /**
     * 增加一个普通字符串数据到form表单数据中  
     * @param name
     * @param value
     * @author dozen.zhang
     */
    public void addTextParameter(String name, String value) {  
        textParams.put(name, value);  
    }  
    /**
     * 增加一个文件到form表单数据中  
     * @param name
     * @param value
     * @author dozen.zhang
     */
    public void addFileParameter(String name, File value) {  
        fileparams.put(name, value);  
    }  
    /**
     * 增加一个文件到form表单数据中  
     * @param name
     * @param value
     * @author dozen.zhang
     */
    public void addFileParameter(String name,CommonsMultipartFile cmFile) {
        cmfileparams.put(name, cmFile);  
    }  
    /**
     * 清空所有已添加的form表单数据  
     * @author dozen.zhang
     */
    public void clearAllParameters() {  
        textParams.clear();  
        fileparams.clear();  
    }  
    /**
     * 发送数据到服务器，返回一个字节包含服务器的返回结果的数组  
     * @return
     * @throws Exception
     * @author dozen.zhang
     */
    public byte[] send() throws Exception {  
        initConnection();  
        try {  
            conn.connect();  
        } catch (SocketTimeoutException e) {  
            // something  
            throw new RuntimeException();  
        }  
        ds = new DataOutputStream(conn.getOutputStream());  
        writeFileParams();  
        writeCmFileParams();  
        writeStringParams();  
        paramsEnd();
        ds.flush();
        InputStream in = conn.getInputStream();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int b;  
        while ((b = in.read()) != -1) {  
            out.write(b);  
        }
        conn.disconnect();  
        return out.toByteArray();  
    }  
    
    /**
     * 文件上传的connection的一些必须设置  
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 连接报错
     */
    private void initConnection() throws Exception {  
        conn = (HttpURLConnection) this.url.openConnection();  
        conn.setDoOutput(true);
        conn.setUseCaches(false);  
        conn.setConnectTimeout(10000); //连接超时为10秒  
        conn.setRequestMethod("POST");
        //如果是 application/json格式的话就不能用这种
        if(fileparams.size()!=0  || cmfileparams.size()!=0)
      conn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
    }  
    /**
     * 普通字符串数据  
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 写数据报错
     */
    private void writeStringParams() throws Exception {  
        Set<String> keySet = textParams.keySet();  
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            String value = textParams.get(name);  
            ds.writeBytes("--" + boundary + "\r\n");  
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                    + "\"\r\n");  
            ds.writeBytes("\r\n");  
            ds.writeBytes(encode(value) + "\r\n");  
        }  
    }  
    /**
     * 文件数据  
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 连接报错
     */
    private void writeFileParams() throws Exception {  
        Set<String> keySet = fileparams.keySet();  
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            File value = fileparams.get(name);  
            ds.writeBytes("--" + boundary + "\r\n");  
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                    + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");  
            ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");  
            ds.writeBytes("\r\n");  
            ds.write(getBytes(value));  
            ds.writeBytes("\r\n");  
        }  
    }  
    /**
     * 文件数据  
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 连接报错
     */
    private void writeCmFileParams() throws Exception {
        Set<String> keySet = cmfileparams.keySet();  
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            CommonsMultipartFile value = cmfileparams.get(name);  
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name
                    + "\"; filename=\"" + encode(value.getOriginalFilename()) + "\"\r\n");
            ds.writeBytes("Content-Type: " + value.getContentType() + "\r\n");  
            ds.writeBytes("\r\n");  
            ds.write(value.getBytes());  
            ds.writeBytes("\r\n");  
        }  
    }
    /**
     * 获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream
     * @param f 文件
     * @return  文件类型
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 连接报错
     */
    private String getContentType(File f) throws Exception {  
        ImageInputStream imagein = ImageIO.createImageInputStream(f);  
        if (imagein == null) {  
            return "application/octet-stream";  
        }  
        Iterator<ImageReader> it = ImageIO.getImageReaders(imagein);  
        if (!it.hasNext()) {  
            imagein.close();  
            return "application/octet-stream";  
        }  
        imagein.close();  
        return "image/" + it.next().getFormatName().toLowerCase();//将FormatName返回的值转换成小写，默认为大写  
  
    }  
    /**
     * 把文件转换成字节数组  
     * @param f 文件
     * @return 数据字节数组
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 连接报错
     */
    private byte[] getBytes(File f) throws Exception {  
        FileInputStream in = new FileInputStream(f);  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        byte[] b = new byte[1024];  
        int n;  
        while ((n = in.read(b)) != -1) {  
            out.write(b, 0, n);  
        }  
        in.close();  
        return out.toByteArray();  
    }  
    /**
     * 添加结尾数据  
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 连接报错
     */
    private void paramsEnd() throws Exception {  
        ds.writeBytes("--" + boundary + "--" + "\r\n");  
        ds.writeBytes("\r\n");  
    }

    /**
     * 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码  
     * @param value 需要转码的字符串
     * @return 转码后的字符串
     * @throws Exception
     * @author dozen.zhang
     * @throws Exception 连接报错
     */
    private String encode(String value) throws Exception{  
        return URLEncoder.encode(value, "UTF-8");  
    }

    public static void main(String[] args) throws Exception {  
        HttpPostUtil u = new HttpPostUtil("http://localhost:3000/up_load");
        u.addFileParameter("img", new File(  
                "D:\\素材\\圆月.jpg"));  
        u.addTextParameter("text", "中文");  
        byte[] b = u.send();  
        String result = new String(b);  
        System.out.println(result);  
  
    }  
}
