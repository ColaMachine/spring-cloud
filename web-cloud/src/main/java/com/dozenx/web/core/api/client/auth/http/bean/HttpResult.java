package com.dozenx.web.core.api.client.auth.http.bean;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 13:43 2018/12/8
 * @Modified By:
 * <p>
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年9月1日 下午1:45:25
 * 创建作者：许小满
 * 文件名称：HttpResult.java
 * 版本：  v1.0
 * 功能：http结果封装
 * 修改记录：
 *//**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年9月1日 下午1:45:25
 * 创建作者：许小满
 * 文件名称：HttpResult.java
 * 版本：  v1.0
 * 功能：http结果封装
 * 修改记录：
 */

import java.io.InputStream;
import java.io.Serializable;

public class HttpResult implements Serializable {

    /** 对象序列号  */
    private static final long serialVersionUID = 3144342617084426863L;

    /** 请求状态码 */
    private int code;

    /** 接口返回接口 */
    private String result;

    /** 接口返回io流 */
    private InputStream inputStream;

    /** 接口请求方式 */
    private String method;

    /** 接口执行花费的时间 */
    private Long costTime;

    /** 构造函数 */
    public HttpResult(int code, String result) {
        super();
        this.code = code;
        this.result = result;
    }

    /** 构造函数 */
    public HttpResult(int code, InputStream inputStream) {
        super();
        this.code = code;
        this.inputStream = inputStream;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
