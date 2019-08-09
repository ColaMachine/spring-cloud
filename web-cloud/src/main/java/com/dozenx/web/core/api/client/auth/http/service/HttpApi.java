/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年9月1日 下午1:42:54
* 创建作者：许小满
* 文件名称：HttpApi.java
* 版本：  v1.0
* 功能：http相关操作接口
* 修改记录：
*/
package com.dozenx.web.core.api.client.auth.http.service;


import com.dozenx.web.core.api.client.auth.http.bean.HttpResult;

public interface HttpApi {

    /**
     * 发送get请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月1日 下午1:44:50
     */
    HttpResult get(String url, String params);
    
    /**
     * 发送get请求，获取inputstream流
     * 注意事项：由接口调用方负责io流的关闭
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月6日 上午1:46:14
     */
    HttpResult getStream(String url, String params);
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    HttpResult post(String url, String params);
    
    /**
     * 发送post请求
     * @param url 接口url
     * @param params 接口参数
     * @param timeout 超时时间，单位：毫秒
     * @return 接口结果
     * @author 许小满  
     * @date 2018年2月23日 下午5:28:58
     */
    HttpResult post(String url, String params, int timeout);
    
    /**
     * 发送post请求体
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月4日 下午11:07:07
     */
    HttpResult postBody(String url, String params);
    
    /**
     * 发送put请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    HttpResult put(String url, String params);
    
    /**
     * 发送put请求体
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    HttpResult putBody(String url, String params);
    
    /**
     * 发送delete请求
     * @param url 接口url
     * @param params 接口参数
     * @return 接口结果
     * @author 许小满  
     * @date 2017年9月5日 上午8:45:22
     */
    HttpResult delete(String url, String params);
    
}
