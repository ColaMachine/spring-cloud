/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年9月4日 上午11:02:47
* 创建作者：许小满
* 文件名称：HttpApiConnectionImpl.java
* 版本：  v1.0
* 功能：采用HttpConnection来发送请求实现类
* 修改记录：
*/
package com.dozenx.web.core.api.client.auth.http.service.impl;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.dozenx.common.exception.InterfaceException;
import com.dozenx.common.util.IoUtil;
import com.dozenx.web.core.api.client.auth.http.bean.HttpResult;
import com.dozenx.web.core.api.client.auth.http.service.HttpApi;
import com.dozenx.web.core.api.client.auth.http.util.HttpUtil;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.util.IOUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy(true)
@Service("httpApiConnectionImpl")
public class HttpApiConnectionImpl implements HttpApi {

    /** 连接超时时间，单位（秒） */
    @Value("#{ ${http.connectTimeout}*1000 }")//转成毫秒
    private int connectTimeout;
    
    /** 读超时时间，单位（秒） */
    @Value("#{ ${http.readTimeout}*1000 }")//转成毫秒
    private int readTimeout;
    
    /**
     * 发送get请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月1日 下午1:44:50
     */
    @Override
    public HttpResult get(String url, String params) {
        int code;//状态码
        String result;//接口返回值
        InputStream is = null;
        try{
            String fullURL = HttpUtil.joinUrlAndParams(url, params);//拼接完整的url
            URL httpURL = new URL(fullURL);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("GET");//设置请求方式
            conn.setDoOutput(false);// 默认情况下是false;
            conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true
            conn.setUseCaches(false);// Get 请求不能使用缓存
            conn.setConnectTimeout(connectTimeout);// 设置 连接主机超时（单位：毫秒）
            conn.setReadTimeout(readTimeout);// 设置从主机读取数据超时（单位：毫秒）
            code = conn.getResponseCode();//状态码
            is = code == 200 ? conn.getInputStream() : conn.getErrorStream();//接口返回值流
            result = IOUtil.ioToString(is);//接口返回值
        } catch (SocketTimeoutException e){
            throw new InterfaceException("E2000078", ErrorMessage.getErrorMsg("E2000078"), url, params, null, e);//接口超时!
        } catch (Exception e){
            throw new InterfaceException("E2000079",ErrorMessage.getErrorMsg("E2000018"), url, params, e);//接口异常!
        } finally {
            IOUtil.close(is);//关闭流，释放资源
        }
        return new HttpResult(code, result);
    }
    
    /**
     * 发送get请求，获取inputstream流
     * 注意事项：由接口调用方负责io流的关闭
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月6日 上午1:46:14
     */
    public HttpResult getStream(String url, String params) {
        int code;//状态码
        InputStream is = null;
        try{
            String fullURL = HttpUtil.joinUrlAndParams(url, params);//拼接完整的url
            URL httpURL = new URL(fullURL);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("GET");//设置请求方式
            conn.setDoOutput(false);// 默认情况下是false;
            conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true
            conn.setUseCaches(false);// Get 请求不能使用缓存
            conn.setConnectTimeout(connectTimeout);// 设置 连接主机超时（单位：毫秒）
            conn.setReadTimeout(readTimeout);// 设置从主机读取数据超时（单位：毫秒）
            code = conn.getResponseCode();//状态码
            is = code == 200 ? conn.getInputStream() : conn.getErrorStream();//接口返回值流
        } catch (SocketTimeoutException e){
            throw new InterfaceException("E2000078", ErrorMessage.getErrorMsg("E2000078"), url, params, null, e);//接口超时!
        } catch (Exception e){
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), url, params, e);//接口异常!
        }
        return new HttpResult(code, is);
    }
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    public HttpResult post(String url, String params){
        return doPost(url, params, connectTimeout, readTimeout);
    }
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @param timeout 超时时间，单位：毫秒
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    public HttpResult post(String url, String params, int timeout){
        return doPost(url, params, timeout, timeout);
    }
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @param connectTimeout 建立连接-超时时间，单位：毫秒
     * @param readTimeout 读-超时时间，单位：毫秒
     * @return 接口结果
     * @author 许小满  
     * @date 2018年2月24日 上午9:04:02
     */
    private HttpResult doPost(String url, String params, int connectTimeout, int readTimeout){
        int code;//状态码
        String result;//接口返回值
        OutputStream os = null;
        InputStream is = null;
        try{
            byte[] paramData = params.getBytes();
            URL httpURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
            conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设定传送的内容类型
            conn.setRequestProperty("Content-Length", String.valueOf(paramData.length));
            conn.setUseCaches(false);// Post 请求不能使用缓存
            conn.setConnectTimeout(connectTimeout);// 设置 连接主机超时（单位：毫秒）
            conn.setReadTimeout(readTimeout);// 设置从主机读取数据超时（单位：毫秒）
            os = conn.getOutputStream();
            os.write(paramData);
            os.flush();
            /* 处理返回值 */
            code = conn.getResponseCode();//状态码
            is = code == 200 ? conn.getInputStream() : conn.getErrorStream();//接口返回值流
            result = IOUtil.ioToString(is);//接口返回值
        } catch (SocketTimeoutException e){
            throw new InterfaceException("E2000078", ErrorMessage.getErrorMsg("E2000078"), url, params, null, e);//接口超时!
        } catch (Exception e){
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), url, params, e);//接口异常!
        } finally {
            IOUtil.close(os);//关闭流，释放资源
            IOUtil.close(is);//关闭流，释放资源
        }
        return new HttpResult(code, result);
    }
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    public HttpResult postBody(String url, String params){
        int code;//状态码
        String result;//接口返回值
        OutputStream os = null;
        InputStream is = null;
        try{
            URL httpURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
            conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
            conn.setUseCaches(false);// Post 请求不能使用缓存
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");// 设定传送的内容类型
            conn.setConnectTimeout(connectTimeout);// 设置 连接主机超时（单位：毫秒）
            conn.setReadTimeout(readTimeout);// 设置从主机读取数据超时（单位：毫秒）
            os = new DataOutputStream(conn.getOutputStream());
            os.write(params.getBytes("UTF-8"));
            os.flush();
            /* 处理返回值 */
            code = conn.getResponseCode();//状态码
            is = code == 200 ? conn.getInputStream() : conn.getErrorStream();//接口返回值流
            result = IOUtil.ioToString(is);//接口返回值
        } catch (SocketTimeoutException e){
            throw new InterfaceException("E2000078", ErrorMessage.getErrorMsg("E2000078"), url, params, null, e);//接口超时!
        } catch (Exception e){
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), url, params, e);//接口异常!
        } finally {
            IOUtil.close(os);//关闭流，释放资源
            IOUtil.close(is);//关闭流，释放资源
        }
        return new HttpResult(code, result);
    }
    
    /**
     * 发送put请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    public HttpResult put(String url, String params){
        int code;//状态码
        String result;//接口返回值
        OutputStream os = null;
        InputStream is = null;
        try{
            byte[] paramData = params.getBytes();
            URL httpURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("PUT");// 设定请求的方法为"POST"，默认是GET
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
            conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设定传送的内容类型
            conn.setRequestProperty("Content-Length", String.valueOf(paramData.length));
            conn.setUseCaches(false);// Post 请求不能使用缓存
            conn.setConnectTimeout(connectTimeout);// 设置 连接主机超时（单位：毫秒）
            conn.setReadTimeout(readTimeout);// 设置从主机读取数据超时（单位：毫秒）
            os = conn.getOutputStream();
            os.write(paramData);
            os.flush();
            /* 处理返回值 */
            code = conn.getResponseCode();//状态码
            is = code == 200 ? conn.getInputStream() : conn.getErrorStream();//接口返回值流
            result = IOUtil.ioToString(is);//接口返回值
        } catch (SocketTimeoutException e){
            throw new InterfaceException("E2000078", ErrorMessage.getErrorMsg("E2000078"), url, params, null, e);//接口超时!
        } catch (Exception e){
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), url, params, e);//接口异常!
        } finally {
            IOUtil.close(os);//关闭流，释放资源
            IOUtil.close(is);//关闭流，释放资源
        }
        return new HttpResult(code, result);
    }
    
    /**
     * 发送put请求体
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    public HttpResult putBody(String url, String params){
        int code;//状态码
        String result;//接口返回值
        OutputStream os = null;
        InputStream is = null;
        try{
            URL httpURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("PUT");// 设定请求的方法为"POST"，默认是GET
            conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
            conn.setUseCaches(false);// Post 请求不能使用缓存
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");// 设定传送的内容类型
            conn.setConnectTimeout(connectTimeout);// 设置 连接主机超时（单位：毫秒）
            conn.setReadTimeout(readTimeout);// 设置从主机读取数据超时（单位：毫秒）
            os = new DataOutputStream(conn.getOutputStream());
            os.write(params.getBytes("UTF-8"));
            os.flush();
            /* 处理返回值 */
            code = conn.getResponseCode();//状态码
            is = code == 200 ? conn.getInputStream() : conn.getErrorStream();//接口返回值流
            result = IOUtil.ioToString(is);//接口返回值
        } catch (SocketTimeoutException e){
            throw new InterfaceException("E2000078",ErrorMessage.getErrorMsg("E2000078"), url, params, null, e);//接口超时!
        } catch (Exception e){
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), url, params, e);//接口异常!
        } finally {
            IOUtil.close(os);//关闭流，释放资源
            IOUtil.close(is);//关闭流，释放资源
        }
        return new HttpResult(code, result);
    }
    
    /**
     * 发送delete请求体
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    public HttpResult delete(String url, String params) {
        int code;//状态码
        String result;//接口返回值
        InputStream is = null;
        try{
            String fullURL = HttpUtil.joinUrlAndParams(url, params);//拼接完整的url
            URL httpURL = new URL(fullURL);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("DELETE");// 设定请求的方法为"POST"，默认是GET
            conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
            conn.setUseCaches(false);// Post 请求不能使用缓存
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");// 设定传送的内容类型
            conn.setConnectTimeout(connectTimeout);// 设置 连接主机超时（单位：毫秒）
            conn.setReadTimeout(readTimeout);// 设置从主机读取数据超时（单位：毫秒）
            /* 处理返回值 */
            code = conn.getResponseCode();//状态码
            is = code == 200 ? conn.getInputStream() : conn.getErrorStream();//接口返回值流
            result = IOUtil.ioToString(is);//接口返回值
        } catch (SocketTimeoutException e){
            throw new InterfaceException("E2000078", ErrorMessage.getErrorMsg("E2000078"), url, params, null, e);//接口超时!
        } catch (Exception e){
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), url, params, e);//接口异常!
        } finally {
            IOUtil.close(is);//关闭流，释放资源
        }
        return new HttpResult(code, result);
    }
    
    

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
    
}
