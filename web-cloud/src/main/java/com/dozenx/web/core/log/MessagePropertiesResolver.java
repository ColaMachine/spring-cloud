package com.dozenx.web.core.log;

import com.dozenx.common.util.FilePathUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 系统错误信息实现类
 * @author 
 */
public class MessagePropertiesResolver {
    public String code;
    public String msg;

    public MessagePropertiesResolver(String desc){
       this.code =  ErrorMessage.getErrorMsgCode(desc);
        this.msg =  ErrorMessage.getErrorMsg(desc);
    }

}
