package com.dozenx.common.exception;

//import com.dozenx.web.core.log.MessagePropertiesResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by dozen.zhang on 2017/11/9.
 */
public class MyException extends RuntimeException{//不需要显示的标记throw Exception
    /** 日志 */
    private static final Log logger = LogFactory.getLog(MyException.class);
    public String code ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String msg;
    public MyException(String code, String msg) {
        super(code+":"+msg);
        this.code =code;
        this.msg =msg;
    }
    public MyException(int code, String msg) {
        super(code+":"+msg);
        this.code =code+"";
        this.msg =msg;
    }
//core里的代码不应该去应用web项目里的东西
//    public InterfaceException(MessagePropertiesResolver messagePropertiesResolver){
//        this.code = messagePropertiesResolver.code;
//        this.msg = messagePropertiesResolver.msg;
//    }




    public void log(){
        logger.error(this.getMessage());
    }
}
