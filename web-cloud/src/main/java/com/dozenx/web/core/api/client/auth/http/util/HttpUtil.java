/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年9月4日 下午1:58:58
* 创建作者：许小满
* 文件名称：HttpUtil.java
* 版本：  v1.0
* 功能：http相关操作工具类
* 修改记录：
*/
package com.dozenx.web.core.api.client.auth.http.util;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.InterfaceException;
import com.dozenx.common.exception.ValidException;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.api.client.auth.http.bean.HttpResult;
import com.dozenx.web.core.api.client.auth.http.service.HttpApi;
import com.dozenx.web.core.api.client.auth.http.service.impl.HttpApiConnectionImpl;
import com.dozenx.web.core.log.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

    /** 日志  */
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    
    /** http api对象 */
    private static HttpApi httpApi;
    
    /**
     * 发送get请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月1日 下午1:44:50
     */
    public static HttpResult get(String url, String params){
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().get(url, params);//发送GET请求
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("GET", url, params, null, httpResult, beginTime);//打印日志
        }
        return httpResult;
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
    public static HttpResult getStream(String url, String params){
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().getStream(url, params);//发送GET请求
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("GET", url, params, null, httpResult, beginTime);//打印日志
        }
        return httpResult;
    }
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    public static HttpResult post(String url, String params) {
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().post(url, params);//发送POST请求
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("POST", url, params, null, httpResult, beginTime);//打印日志
        }
        return httpResult;
    }
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @param timeout 接口超时时间，单位：毫秒
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    public static HttpResult post(String url, String params, int timeout) {
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().post(url, params, timeout);//发送POST请求
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("POST", url, params, null, httpResult, beginTime);//打印日志
        }
        return httpResult;
    }
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    public static HttpResult postBody(String url, String params) {
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().postBody(url, params);//发送POST请求体
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("POST", url, null, params, httpResult, beginTime);//打印日志
        }
        return httpResult;
    }
    
    /**
     * 发送put请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    public static HttpResult put(String url, String params){
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().put(url, params);//发送PUT请求
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("PUT", url, params, null, httpResult, beginTime);//打印日志
        }
        return httpResult;
    }
    
    /**
     * 发送put请求体
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    public static HttpResult putBody(String url, String params){
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().putBody(url, params);//发送PUT请求体
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("PUT", url, null, params, httpResult, beginTime);//打印日志
        }
        return httpResult;
    }
    
    /**
     * 发送delete请求体
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    public static HttpResult delete(String url, String params) {
        long beginTime = System.currentTimeMillis();//接口调用触发时间
        HttpResult httpResult = null;
        try{
            httpResult = getHttpApi().delete(url, params);//发送DELETE请求
        } catch (InterfaceException e){
            throw e;
        } finally {
            log("DELETE", url, null, params, httpResult, beginTime);//打印日志
        }
        return httpResult;
    }
    
    /**
     * 将url和参数进行拼接
     * @param url 接口url
     * @param param 接口参数
     * @return 拼接后的完整url
     * @author 许小满  
     * @date 2017年9月1日 下午5:08:24
     */
    public static String joinUrlAndParams(String url, String param){
        if(StringUtil.isBlank(url)){
            throw new ValidException("E0000002", ErrorMessage.getErrorMsg("E0000002", "接口地址"));//{0}不允许为空!
        }
        if(StringUtil.isBlank(param)){
            return url;
        }
        return (url.indexOf("?") > -1) ? url + "&" + param : url + "?" + param;
    }
    
    /**
     * 获取参数
     * @param paramMap 请求参数
     * @return new paramMap
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年9月5日 下午7:20:49
     */
    public static String getParams(Map<String, String> paramMap){
        if(paramMap == null || paramMap.size()<= 0){
            return StringUtil.EMPTY;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String value = StringUtil.defaultString(entry.getValue());
            try {
                params.append(key).append('=').append(URLEncoder.encode(value, "UTF-8")).append('&');
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(),e);
                throw new BizException("E2000019", "UnsupportedEncoding");//未知异常
            }
        }
        int paramsLength = params.length();
        if(paramsLength > 0){
            params.deleteCharAt(params.length() - 1);
        }
        return params.toString();
    }
    
    /**
     * 接口日志输出规范话
     * @param method http请求方式
     * @param url 请求url
     * @param params 请求参数
     * @param bodyParams 请求体参数
     * @param httpResult 返回数据
     * @param beginTime 接口调用开始时间
     * @author 许小满  
     * @date 2017年2月22日 上午11:29:01
     */
    private static void log(String method, String url, String params, String bodyParams, HttpResult httpResult, long beginTime){
        /* 数据赋值，用于记录日志 */
        boolean httpResultNotNull = httpResult != null;
        long costTime = System.currentTimeMillis()-beginTime;
        if(httpResultNotNull) {
            httpResult.setCostTime(costTime);//接口执行花费的时间
            httpResult.setMethod(method);//接口请求方式
        }
        
        //如果code==200，日志级别只支持error，流程自动结束
        int code = -1;//状态码
        boolean errorLevel = true;//异常级别：默认error
        if(httpResultNotNull){
            code = httpResult.getCode();//状态码
            if(code >= 200 && code < 400){
                errorLevel = false;
            }
            if(!errorLevel && !logger.isDebugEnabled()){//code正常 且 日志级别高于debug时，不输出日志
                return;
            }
        }
        //日志内容格式化输出
        StringBuilder log = new StringBuilder();
        log.append("提示：接口http请求方式（").append(method).append("），");
        if(StringUtil.isNotBlank(url)){
            log.append("请求url（").append(url).append("），");
        }
        if(StringUtil.isNotBlank(params)){
            if(params.length()>300){
                params=params.substring(0,300);
            }
            log.append("请求参数（").append(params).append("），");
        }
        if(StringUtil.isNotBlank(bodyParams)){
            if(bodyParams.length()>300){
                bodyParams=bodyParams.substring(0,300);
            }
            log.append("请求体参数（").append(bodyParams).append("），");
        }
        if(httpResultNotNull){
            String result = httpResult.getResult();//接口返回值
            if(result != null){
                int maxLength = result.length();
                if(maxLength > 4000){//长度截取，防止日志输出太多
                    result = result.substring(0, 4000) + "...";
                }
                log.append("返回数据（").append(result).append("），");
            }
            InputStream is = httpResult.getInputStream();//文件流
            if(is != null){
                log.append("返回数据（文件io流），");
            }
            log.append("状态码（").append(code).append("），");
        }
        log.append("共花费了 ").append(costTime).append(" ms.");
        if(errorLevel){
            logger.error(log.toString());
        } else {
            logger.debug(log.toString());
        }
    }
    
    /**
     * 获取 http api 对象
     * @return 获取 http api 对象
     * @author 许小满  
     * @date 2017年9月4日 下午2:43:07
     */
    private static HttpApi getHttpApi(){
//        if(httpApi == null){
//            HttpApiFactory httpApiFactory = (HttpApiFactory)BeanUtil.getBean("httpApiFactory");
//            httpApi = httpApiFactory.create();
//        }
        return new HttpApiConnectionImpl();
    }
    
}
