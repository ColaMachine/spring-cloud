/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月1日 下午8:18:43
* 创建作者：许小满
* 文件名称：OpenTokenHttpRequest.java
* 版本：  v1.0
* 功能：开放平台http接口-须提供token，相关公共代码抽取,目前仅允许在CenterHttpRequest.java、AuthHttpRequest.java中使用
* 修改记录：
*/
package com.dozenx.web.core.api.client.auth.http.util;

import com.dozenx.common.exception.InterfaceException;
import com.dozenx.common.exception.ValidException;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.api.client.auth.http.bean.HttpResult;
import com.dozenx.web.core.api.client.auth.http.bean.TokenHttpResult;
import com.dozenx.web.core.log.ErrorMessage;

import java.util.Map;


@SuppressWarnings("unchecked")
public class TokenHttpUtil {
    
    /**
     * 请求url添加access_token
     * @param url 接口url
     * @return 参数
     * @author 许小满  
     * @date 2017年9月5日 下午7:12:23
     */
    public static String urlAddAccessToken(String url){
        if(StringUtil.isBlank(url)){
            throw new ValidException("E0000002", ErrorMessage.getErrorMsg("E0000002", "接口地址"));//{0}不允许为空!
        }
        StringBuilder newURL = new StringBuilder(url);//生成的新的url，自动拼接access_token
        if(url.indexOf("?") == -1){//不包含?时
            newURL.append("?").append("access_token=").append(TokenClient.getAccessToken());
        } else {//包含?时
            newURL.append("&").append("access_token=").append(TokenClient.getAccessToken());
        }
        return newURL.toString();
    }
    
    /**
     * 请求参数重置access_token
     * @param url 接口url
     * @return 参数
     * @author 许小满  
     * @date 2017年9月5日 下午7:12:23
     */
    public static String urlResetAccessToken(String url){
        if(StringUtil.isBlank(url)){
            throw new ValidException("E0000002", ErrorMessage.getErrorMsg("E0000002", "接口地址"));//{0}不允许为空!
        }
        StringBuilder newURL = new StringBuilder(url);//生成的新的url，自动拼接access_token
        if(url.indexOf("?") == -1){//不包含?时
            newURL.append("?").append("access_token=").append(TokenClient.resetAccessToken());
        } else {//包含?时
            newURL.append("&").append("access_token=").append(TokenClient.resetAccessToken());
        }
        return newURL.toString();
    }
    
    
    /**
     * 分析第一次发送请求结果
     * @param httpResult 接口返回结果
     * @param path path
     * @param params 请求参数
     * @return 接口返回值
     * @author 许小满  
     * @date 2017年9月4日 下午6:28:11
     */
    public static TokenHttpResult analysisPriResult(HttpResult httpResult, String path, String params){
        int code =  httpResult.getCode();//接口状态码
        String result = httpResult.getResult();//接口返回值
        /* "200" 直接返回成功信息 */
        if(code == 200){
            return new TokenHttpResult(code, result);
        }
        /* 400 Bad Request 请求出现语法错误  */
        else if (code == 400){
            throw new InterfaceException("04640129211",ErrorMessage.getErrorMsg("E2000023"), path, params);//参数不符合规范!
        }
        /* 403  Forbidden  资源不可用。服务器理解客户的请求，但拒绝处理它，通常由于服务器上文件或目录的权限设置导致。  */
        else if(code == 403){//针对数据中心token失效
            if(StringUtil.isBlank(result)){
                //非token失效，则抛出异常信息
                throw new InterfaceException("04640129211",ErrorMessage.getErrorMsg("E2000009"), path, params);//接口无返回值!
            }
            Map<String,Object> returnMap = JsonUtil.fromJson(result, Map.class);
            String errorMessage = (String) returnMap.get("errormessage");//错误信息
            if(StringUtil.isBlank(errorMessage)){
                throw new InterfaceException(ErrorMessage.getErrorMsg("E0000002", "errorMessage"), path, params, result);//{0}不允许为空!
            }
            if(errorMessage.indexOf("凭证无效") > -1){
                return new TokenHttpResult(code, result);
            } else {
                throw new InterfaceException(errorMessage, path, params, result);//接口异常!
            }
        }
        /* 其它状态码  */
        else {
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), path, params, result);//接口异常!
        }
    }
    
    /**
     * 对二次请求返回的信息格式化，统一返回
     * @param httpResult 接口返回结果
     * @param path path
     * @param params 请求参数
     * @return 接口返回值
     * @author 许小满  
     * @date 2017年9月4日 下午7:45:33
     */
    public static TokenHttpResult analysisSecResult(HttpResult httpResult, String path, String params){
        int code = httpResult.getCode();//状态码
        String result = httpResult.getResult();//接口返回值
        /* "200" 直接返回成功信息 */
        if(code == 200){
            return new TokenHttpResult(code, result);
        }
        /* 400 Bad Request 请求出现语法错误 */
        else if (code == 400){
            throw new InterfaceException("04640129211",ErrorMessage.getErrorMsg("E2000023"), path, params);//参数不符合规范!
        }
        /* 403  Forbidden  资源不可用。服务器理解客户的请求，但拒绝处理它，通常由于服务器上文件或目录的权限设置导致。  */
        else if(code == 403){ //"403"
            if(StringUtil.isBlank(result)){
                //非token失效，则抛出异常信息
                throw new InterfaceException("04640129212",ErrorMessage.getErrorMsg("E2000009"), path, params);//接口无返回值!
            }
            Map<String,Object> returnMap = JsonUtil.fromJson(result, Map.class);
            String errorMessage = (String) returnMap.get("errormessage");//错误信息
            if(StringUtil.isBlank(errorMessage)){
                throw new InterfaceException(ErrorMessage.getErrorMsg("E0000002", "errorMessage"), path, params, result);//{0}不允许为空!
            }
            throw new InterfaceException(errorMessage, path, params, result);//接口异常!
        }
        /* 其它状态码 */
        else {
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), path, params, result);//接口异常!
        }
    }
    
}
