/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年9月5日 下午7:38:40
* 创建作者：许小满
* 文件名称：TokenHttpResult.java
* 版本：  v1.0
* 功能：http结果封装
* 修改记录：
*/
package com.dozenx.web.core.api.client.auth.http.bean;

import java.io.Serializable;

public class TokenHttpResult implements Serializable{

    /** 对象序列号 */
    private static final long serialVersionUID = -9119104783299123634L;

    /** 请求状态码 */
    private int code;
    
    /** 接口返回接口 */
    private String result;
    
    /** 构造函数 */
    public TokenHttpResult(int code, String result) {
        super();
        this.code = code;
        this.result = result;
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
    
}
