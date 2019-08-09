package com.dozenx.web.core.log.service;

import com.dozenx.common.util.NetWorkUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.log.LogKey;
import com.dozenx.web.core.log.LogType;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.bean.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

//import com.awifi.core.log.collection.api.client.LogSendClient;


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
@Service
public class LogUtilService {
    private static final Logger logger = LoggerFactory.getLogger(LogUtilService.class);
    /** SERVICE_IP */
    private static final String SERVICE_IP = NetWorkUtil.getLocalIPAddress();
    @Resource
    SysLogService sysLogService;



    public static void main(String args[]) {
        System.out.println(LogType.TRACK.ordinal());
    }
    /**
     * 记录日志
     */
    /**
     * @param serviceCode
     *            方法名
     * @param type
     *            消息类型 用来区分紧急程度
     * @param detailCode
     *            独立于方法内的消息code 注意独立值
     * @param param
     *            参数 在一次请求的非第一次调用阶段可以为空
     * @param msg
     *            消息
     * @param username
     *            用户名
     * @author dozen.zhang
     */
    public void log(ServiceCode serviceCode, LogType type, int detailCode, String param, String msg, String username) {
        try {
            String globalValue = new LogKey().get();
            if (StringUtil.isBlank(globalValue)) {
                new LogKey().init(username + "-" + new Date().getTime());
                globalValue = new LogKey().get();
            }
            // System.out.println(globalValue);
            int codeInt = type.getValue() * 1000000 + (((serviceCode.ordinal() + 1) * 1000 + detailCode));
            String s = "" + codeInt;
            String code = "000000".substring(0, (8 - s.length()))
                    + ((serviceCode.ordinal() + 1) * 100000 + (type.ordinal() + 1) * 1000 + detailCode);
       /* String paramInfo = DCLogUtil.parseCenterSysLog(SERVICE_IP, code, "" + serviceCode, "save", param,
                "MSP-" + serviceCode, globalValue, msg);*/

            Throwable ex = new Throwable();
            StackTraceElement[] stackElements = ex.getStackTrace();
            String stacktrace = "";
            StringBuffer sb = new StringBuffer();
            String path = "";
            if (stackElements != null) {
                for (int i = 2; i < stackElements.length; i++) {
                    if (!"com.util.log.LogUtil".equals(stackElements[i].getClassName())) {
                        // stacktrace=stackElements[i].getClassName()+":"+stackElements[i].getLineNumber()+stackElements[i].getMethodName()
                        path = stackElements[i].getClassName()
                                + "." + stackElements[i].getMethodName() + "(" + stackElements[i].getFileName() + ":" + stackElements[i].getLineNumber() + ")";
                        sb.append("").append(path).append(": ");
                        break;
                    }

                }
            }
        /*
         * WebApplicationContext wac =
         * ContextLoader.getCurrentWebApplicationContext(); SysLogService
         * sysLogService = (SysLogService)wac.getBean("sysLogService");
         */
            SysLog sysLog = new SysLog();
            sysLog.setCode(codeInt);
            sysLog.setPath(path);
            sysLog.setCreateTime(new Timestamp(new Date().getTime()));
            sysLog.setMsg(msg);
            sysLog.setParam(param);
            Long timeStamp = 0l;
            String userName = "";
            if (StringUtil.isNotBlank(globalValue)) {

                try {
                    if (globalValue.indexOf("-") > 0) {
                        timeStamp = Long.valueOf(globalValue.split("-")[1]);
                        userName = globalValue.split("-")[0];
                    } else if (globalValue.indexOf("-") == 0) {
                        timeStamp = Long.valueOf(globalValue.substring(1));
                    }
                } catch (Exception e) {
                    timeStamp = new Date().getTime();
                } finally {
                    if (timeStamp == null || timeStamp < new Date().getTime() - 1000000) {
                        timeStamp = new Date().getTime();
                    }
                }
            } else {
                timeStamp = new Date().getTime();
                userName = username;
            }
            sysLog.setStartTime(new Timestamp(timeStamp));
            sysLog.setUser(userName);
            sysLog.setType(Byte.valueOf("" + type.ordinal()));
            sysLogService.save(sysLog);
            // 路径 时间 开始时间 用户 日志类型 日志代码 消息 参数
            sb.append("【").append(globalValue).append("】【").append(type).append("】【").append(msg).append("】【").append(param)
                    .append("】【").append(new Date().getTime()).append("】【").append(code).append("】");

        /*
         * try { RedisUtil.lpush("msp-log", sb.toString()); } catch (Exception
         * e) { // TODO Auto-generated catch block e.printStackTrace(); }
         */
            if (type == LogType.SERVICE || type == LogType.PARAM || type == LogType.THIRD || type == LogType.SYSTEM
                    || type == LogType.UNKNOW) {
                // logger.error(sb.toString());
                System.err.print(sb.toString());
                //LogSendClient.sendErrorLog(paramInfo);
            } else {
                //logger.info(sb.toString());
                System.out.println(sb.toString());
                //LogSendClient.sendInfoLog(paramInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 系统错误
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void error(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
       // String s = "" + LogType.SYSTEM.getValue() * 100000 +  (((serviceCode.ordinal() + 1)* 1000 + detailCode));
        log(serviceCode, LogType.SERVICE, detailCode, param, msg, username);
    }

    /**
     * 服务逻辑错误
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void service(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
       // String s = "" + (((serviceCode.ordinal() + 1) * 100000 + LogType.SYSTEM.ordinal() * 1000 + detailCode));
        log(serviceCode, LogType.SERVICE, detailCode, param, msg, username);
    }

    /**
     * 系统错误
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void system(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
       // String s = "" + (((serviceCode.ordinal() + 1) * 100000 + LogType.SYSTEM.ordinal() * 1000 + detailCode));
        log(serviceCode, LogType.SYSTEM, detailCode, param, msg, username);
    }

    /**
     * 前端参数错误
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void param(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
      //  String s = "" + (((serviceCode.ordinal() + 1) * 100000 + LogType.PARAM.ordinal() * 1000 + detailCode));
        log(serviceCode, LogType.PARAM, detailCode, param, msg, username);

    }

    /**
     * 普通跟踪打印
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void track(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
      //  String s = "" + (((serviceCode.ordinal() + 1) * 100000 + LogType.TRACK.ordinal() * 1000 + detailCode));
        log(serviceCode, LogType.TRACK, detailCode, param, msg, username);

    }

    /**
     * 初始化global key
     * 
     * @param key
     * @author dozen.zhang
     */
    public void init(String key) {
        new LogKey().init(key + "-" + new Date().getTime());
    }

    /**
     * 通知用户的消息 导致流程走不下去 是因为某些正常的原因
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void info(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
       // String s = "" + (((serviceCode.ordinal() + 1) * 100000 + LogType.INFO.ordinal() * 1000 + detailCode));
        log(serviceCode, LogType.INFO, detailCode, param, msg, username);

    }

    /**
     * 未知错误
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void unknow(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
       // String s = "" + (((serviceCode.ordinal() + 1) * 100000 + LogType.UNKNOW.ordinal() * 1000 + detailCode));
        log(serviceCode, LogType.UNKNOW, detailCode, param, msg, username);

    }

    /**
     * 第三方错误 error 级别
     * 
     * @param serviceCode
     * @param detailCode
     * @param param
     * @param msg
     * @param username
     * @author dozen.zhang
     */
    public void third(ServiceCode serviceCode, int detailCode, String param, String msg, String username) {
       // String s = "" + (((serviceCode.ordinal() + 1) * 100000 + LogType.THIRD.ordinal() * 1000 + detailCode));
        log(serviceCode, LogType.THIRD, detailCode, param, msg, username);

    }
}
