package com.dozenx.common.exception;

//import com.dozenx.web.core.log.MessagePropertiesResolver;

import com.dozenx.common.exception.MyException;

/**
 * Created by dozen.zhang on 2017/11/9.
 */
public class InterfaceException extends MyException {
    public InterfaceException(String code, String msg) {
        super(code,msg);

    }

    public InterfaceException(String code, String msg,String url,String param) {
        super(code,msg+" url:"+url+" param:"+param);

    }

    public InterfaceException(String msg,String url,String param,Exception e) {
        super("\"E2000078\", ",msg+" url:"+url+" param:"+param);

    }


    public InterfaceException(String code, String msg,String url,String param,Exception e) {
        super(code,msg+" url:"+url+" param:"+param);

    }
    public InterfaceException(String code, String msg,String url,String param,String result ,Exception e) {
        super(code,msg+" url:"+url+" param:"+param);

    }


    public InterfaceException(int code, String msg) {
        super(code,msg);

    }
//core里的代码不应该去应用web项目里的东西
//    public InterfaceException(MessagePropertiesResolver messagePropertiesResolver){
//        this.code = messagePropertiesResolver.code;
//        this.msg = messagePropertiesResolver.msg;
//    }
}
