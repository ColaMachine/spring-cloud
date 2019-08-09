package com.dozenx.common.exception;

import com.dozenx.common.exception.MyException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 下午2:08:42
 * 创建作者：许小满
 * 文件名称：ValidException.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class ValidException extends MyException {

    /** 序列号 */
    private static final long serialVersionUID = 3393238725135126965L;
    
    /** 日志 */
    private static final Log logger = LogFactory.getLog(ValidException.class);

    public ValidException(String code, String msg) {
        super(code,msg);

    }
    public ValidException(int code, String msg) {
        super(code,msg);

    }


}
