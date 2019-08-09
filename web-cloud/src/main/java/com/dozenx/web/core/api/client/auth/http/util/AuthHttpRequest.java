package com.dozenx.web.core.api.client.auth.http.util;

import com.dozenx.common.exception.InterfaceException;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.api.client.auth.http.bean.HttpResult;
import com.dozenx.web.core.api.client.auth.http.bean.TokenHttpResult;
import com.dozenx.web.core.log.ErrorMessage;

import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:47 2018/12/7
 * @Modified By:
 */
public class AuthHttpRequest {


    /**
     * 调用数据中心接口，发送get请求
     * @param url 接口地址
     * @param params 参数
     * @return map
     * @author 许小满
     * @date 2017年9月5日 下午8:12:54
     */
    public static Map<String, Object> sendGetRequest(String url, String params){
        //1.发送请求(第一次)
        String priURL = TokenHttpUtil.urlAddAccessToken(url);//请求参数添加access_token
        HttpResult httpResult = HttpUtil.get(priURL, params);//发送get请求
        //2.分析请求返回结果（第一次）
        TokenHttpResult tokenHttpResult = TokenHttpUtil.analysisPriResult(httpResult, priURL, params);//分析第一次发送请求结果
        if(tokenHttpResult.getCode() == 403){//状态码为403，代表access_token已经失效
            //3.发送请求(第二次)
            String secURL = TokenHttpUtil.urlResetAccessToken(url);//请求参数重置access_token
            httpResult = HttpUtil.get(secURL, params);//发送get请求
            //4.分析请求返回结果（第二次）
            tokenHttpResult = TokenHttpUtil.analysisSecResult(httpResult, secURL, params);//分析第一次发送请求结果
        }
        //5.分析接口返回值，并转成map
        return analysisResultToMap(url, params , tokenHttpResult.getResult());
    }


    public static Map<String, Object> sendPostRequest(String url, String params){
        //1.发送请求(第一次)
        String priURL = TokenHttpUtil.urlAddAccessToken(url);//请求参数添加access_token
        HttpResult httpResult = HttpUtil.postBody(priURL, params);//发送get请求
        //2.分析请求返回结果（第一次）
        TokenHttpResult tokenHttpResult = TokenHttpUtil.analysisPriResult(httpResult, priURL, params);//分析第一次发送请求结果
        if(tokenHttpResult.getCode() == 403){//状态码为403，代表access_token已经失效
            //3.发送请求(第二次)
            String secURL = TokenHttpUtil.urlResetAccessToken(url);//请求参数重置access_token
            httpResult = HttpUtil.get(secURL, params);//发送get请求
            //4.分析请求返回结果（第二次）
            tokenHttpResult = TokenHttpUtil.analysisSecResult(httpResult, secURL, params);//分析第一次发送请求结果
        }
        //5.分析接口返回值，并转成map
        return analysisResultToMap(url, params , tokenHttpResult.getResult());
    }

    /**
     * 分析接口返回值，并转成map
     * @param interfaceUrl url
     * @param interfaceParam 参数
     * @param interfaceResult 接口返回值
     * @return map
     * @author 许小满
     * @date 2017年9月4日 下午6:46:44
     */
    private static Map<String, Object> analysisResultToMap(String interfaceUrl, String interfaceParam, String interfaceResult){
        if(StringUtil.isBlank(interfaceResult)){
            throw new InterfaceException("E046401201",ErrorMessage.getErrorMsg("E2000009"), interfaceUrl, interfaceParam);//接口无返回值!
        }
        Map<String, Object> resultMap = JsonUtil.fromJson(interfaceResult, Map.class);//将字符串转成json
        if(resultMap == null || resultMap.isEmpty()){//未得到结果
            throw new InterfaceException("E046401201",ErrorMessage.getErrorMsg("E2000009"),interfaceUrl, interfaceParam);//接口返回值转map后为空！
        }
        String resultCode = (String) resultMap.get("code");
        if(StringUtil.isBlank(resultCode) || !resultCode.equals("0")){
            throw new InterfaceException((String) resultMap.get("message"), interfaceUrl, interfaceParam, interfaceResult);
        }
        return resultMap;
    }
}
