package com.dozenx.web.core.log;

import com.dozenx.common.util.NetWorkUtil;
import com.dozenx.common.util.StringUtil;

import java.util.Date;

/*
 * 编码详解 00111001
 *第一组3位数字表示service名
 *第二组2为数字表示错误类型 第一位代表大类，第二位可细分小类
 *1system 系统级别错误
 *2param 前端参数错误
 *3service 业务级别错误
 *4third 依赖错误 内部调用第三方服务出错
 *5info交互级别提醒 需要通知用户 如验证码不正确
 *99unknow未知异常
 *第三组3位数字表示明细错误
 */
public class LogUtilFeichu {
    
    /** SERVICE_IP */
    private static final String SERVICE_IP = NetWorkUtil.getLocalIPAddress();
    
    /**
     * @param serviceCode 方法名
     * @param type 消息类型 用来区分紧急程度
     * @param detailCode 独立于方法内的消息code 注意独立值
     * @param param 参数 在一次请求的非第一次调用阶段可以为空
     * @param msg 消息
     * @param username 用户名
     * @author dozen.zhang
     */
    public static void log(ServiceCode serviceCode, LogType type, int detailCode, String param, String msg, String username){
        String globalValue= new LogKey().get();
        if(StringUtil.isBlank(globalValue)){
            new LogKey().init(username+"-"+new Date().getTime());
        }
        String s = ""+(((serviceCode.ordinal()+1)*100000+type.ordinal()*1000+detailCode));
       // String paramInfo = DCLogUtil.parseCenterSysLog(SERVICE_IP, ""+"000000".substring(0, (8- s.length()))+((serviceCode.ordinal()+1)*100000+type.ordinal()*1000+detailCode), ""+serviceCode,
         //       "save",param, "OPMS-"+serviceCode, globalValue, msg);
        if(type.ordinal()>= LogType.SERVICE.ordinal()){
           // LogSendClient.sendErrorLog(paramInfo);
        }else{
            //LogSendClient.sendInfoLog(paramInfo);
        }
    }
    public static String getGlobalKey(){
        return "OPMS";
    }
    public static String getGlobalValue(){
        String globalValue= new LogKey().get();
        return globalValue;
    }
    /**
     * 系统错误
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public static void system(ServiceCode serviceCode, int detailCode, String param, String msg, String username){
        String s = ""+(((serviceCode.ordinal()+1)*100000+ LogType.SYSTEM.ordinal()*1000+detailCode));
        log( serviceCode, LogType.SYSTEM,detailCode, param, msg, username);

    }
    /**
     * 参数打印
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public static void param(ServiceCode serviceCode, int detailCode, String param, String msg, String username){
        String s = ""+(((serviceCode.ordinal()+1)*100000+ LogType.PARAM.ordinal()*1000+detailCode));
        log( serviceCode, LogType.PARAM,detailCode, param, msg, username);

    }
    /**
     * 普通打印
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public static void track(ServiceCode serviceCode, int detailCode, String param, String msg, String username){
        String s = ""+(((serviceCode.ordinal()+1)*100000+ LogType.TRACK.ordinal()*1000+detailCode));
        log( serviceCode, LogType.TRACK,detailCode, param, msg, username);

    }
    /**
     * 初始化global key
     * @author dozen.zhang
     */
    public static void init(String username){
        new LogKey().init(username+new Date().getTime());
    }
    /**
     * 通知用户的
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public static void info(ServiceCode serviceCode, int detailCode, String param, String msg, String username){
        String s = ""+(((serviceCode.ordinal()+1)*100000+ LogType.INFO.ordinal()*1000+detailCode));
        log( serviceCode, LogType.INFO,detailCode, param, msg, username);

    }
    /**
     * 未知错误
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public static void unknow(ServiceCode serviceCode, int detailCode, String param, String msg, String username){
        String s = ""+(((serviceCode.ordinal()+1)*100000+ LogType.UNKNOW.ordinal()*1000+detailCode));
        log( serviceCode, LogType.UNKNOW,detailCode, param, msg, username);

    }
    /**
     * 第三方错误
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public static void third(ServiceCode serviceCode, int detailCode, String param, String msg, String username){
        String s = ""+(((serviceCode.ordinal()+1)*100000+ LogType.THIRD.ordinal()*1000+detailCode));
        log( serviceCode, LogType.THIRD,detailCode, param, msg, username);
       
    }
}
