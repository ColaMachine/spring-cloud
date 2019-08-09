package com.dozenx.common.util;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.HashMap;

import javax.net.ssl.*;


import com.dozenx.common.util.FileUtil;
import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.https.Handler;

/**
 * @author hongwang.zhang
 * https请求类
 *
 */
public class HttpsConnection {
    private static final Logger logger = LoggerFactory.getLogger(HttpsConnection.class);

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String DEFAULT_CHARSET = "utf-8";

    public static String doPost(String url, String params, String charset, int connectTimeout, int readTimeout,boolean getLocation) throws Exception {
        String ctype = "text/html;charset=" + charset;
        byte[] content = {};
        if(params != null){
            content = params.getBytes(charset);
        }
        return doPost(url, ctype, content, connectTimeout, readTimeout,getLocation);
    }
    public static String doPost(String url, String ctype, byte[] content,int connectTimeout,int readTimeout,boolean getLocation) throws Exception {
        HttpsURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try{
                SSLContext ctx = SSLContext.getInstance("SSL");
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
                SSLContext.setDefault(ctx);

                conn = getConnection(new URL(null,url,new Handler()), METHOD_POST, ctype);
                conn.setRequestMethod("POST");
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            }catch(Exception e){
                logger.error("doPost GET_CONNECTOIN_ERROR, URL = " + url, e);
                throw e;
            }
            try{
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
                if (rsp!=null) {
                    if (getLocation) {
                        String location = conn.getHeaderField("Location");
                        if (location!=null) {
                            return location.substring(location.lastIndexOf("/")+1, location.length());
                        }
                    }
                }
            }catch(IOException e){
                logger.error("doPost REQUEST_RESPONSE_ERROR, URL = " + url, e);
                throw e;
            }

        }finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    public static String doGet(String url, String ctype, int connectTimeout,int readTimeout) throws Exception {
        HttpsURLConnection conn = null;
        String rsp = null;
        try {
            try{
                SSLContext ctx = SSLContext.getInstance("SSL");
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
                SSLContext.setDefault(ctx);

                conn = getConnection(new URL(null,url,new Handler()), METHOD_GET, ctype);
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            }catch(Exception e){
                logger.error("doGet GET_CONNECTOIN_ERROR, URL = " + url, e);
                throw e;
            }
            try{
                rsp = getResponseAsString(conn);
            }catch(IOException e){
                logger.error("doGet REQUEST_RESPONSE_ERROR, URL = " + url, e);
                throw e;
            }

        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static class DefaultTrustManager implements X509TrustManager {

        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
         */
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
         */
        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
         */
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }


    }

    private static HttpsURLConnection getConnection(URL JNurl, String method, String ctype)
            throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) JNurl.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        //conn.setRequestProperty("Accept", "text/plain");
        //conn.setRequestProperty("User-Agent", "stargate");
        //conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    protected static String getResponseAsString(HttpsURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
//            throw new IOException( "未获取到https 连接打开失败 为null");
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            logger.error("https error : "+msg);
            if (com.dozenx.common.util.StringUtil.isBlank(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    protected static void saveResponseAsFile(HttpsURLConnection conn,String fileName,Path path) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
//            throw new IOException( "未获取到https 连接打开失败 为null");
            FileUtil.writeFileFromStream(conn.getInputStream(), fileName,path);
        } else {//说明发生错误了
            String msg = getStreamAsString(es, charset);
            logger.error("https error : "+msg);
            if (com.dozenx.common.util.StringUtil.isBlank(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }


        }
    }


    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!com.dozenx.common.util.StringUtil.isBlank(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtil.isBlank(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }


    public static void saveFileFromUrl(String url,String fileName,Path path) throws Exception {
        HttpsURLConnection conn = null;

        try {
            try{
                SSLContext ctx = SSLContext.getInstance("SSL");
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
                SSLContext.setDefault(ctx);

                conn = getConnection(new URL(null,url,new Handler()), METHOD_GET, null);
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
            }catch(Exception e){
                logger.error("doGet GET_CONNECTOIN_ERROR, URL = " + url, e);
                throw e;
            }
            try{
                saveResponseAsFile(conn,fileName,path);
            }catch(IOException e){
                logger.error("doGetFileFrom url error REQUEST_RESPONSE_ERROR, URL = " + url, e);
                throw e;
            }

        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }


    }


    public static void main(String args[]){
//
//        String url = "http://192.168.41.53/sms-service/sms/send?mobile=18368729738&msg=%E4%BA%BA%E8%84%B8%E8%AF%86%E5%88%AB%E5%A4%B1%E8%B4%A5&access_token=5aaf89c1bfaed238e1eea6c1";
//
//        String result = HttpRequestUtil.sendGet(url);
//        System.out.println(result);

        String url = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2146738681,2416731710&fm=173&app=25&f=JPEG?w=218&h=146&s=2B120AC162D71FD60A9DE41F0300D093";

        String result = null;
        try {
            String s =   HttpsConnection.doGet(url,null,2000,2000);
            System.out.println(s);
            HttpsConnection.saveFileFromUrl(url,"saveFileFromUrlPic", Paths.get("g:/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

}

