/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月22日 下午7:52:51
* 创建作者：许小满
* 文件名称：ExceptionLogUtil.java
* 版本：  v1.0
* 功能：异常日志工具类
* 修改记录：
*/
package com.dozenx.web.core.log;

import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.log.bean.OperLog;
import com.dozenx.web.core.log.service.OperLogService;
import com.dozenx.web.util.BeanUtil;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.SessionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;


public class OperLogUtil {

    /** 日志 */
    private static Log logger = LogFactory.getLog(OperLogUtil.class);

    /** 异常日志service */
    private static OperLogService operLogService;

    /**
     * 操作详情最大长度
     */
    private static int MAX_LENTH_DETAIL = 21840;// 65535/3=21845

    /**
     * 保存操作日志.
     *
     * @param request request
     * @param moduleName 操作模块
     * @param compName 操作对象
     * @param detail 日志详情
     * @author dozen.zhang
     * @date 2017年6月22日 下午8:02:04
     */
    public static void add(HttpServletRequest request, String moduleName, String compName, String detail) {
        try {
            // String logSwitch = SysConfigUtil.getParamValue("error_log_switch");// 异常日志开关：ON代表开、OFF代表关
            OperLog operLog = new OperLog();

            try {
                SessionUser user = SessionUtil.getCurSessionUser(request);
                if (user != null) {
                    operLog.setUserId(user.getUserId());
                    operLog.setUserName(user.getAccount());
                }
            } catch (Exception e) {
                logger.error("取不到用户信息", e);
            }
            if (StringUtil.isNotBlank(detail) && detail.length() > MAX_LENTH_DETAIL) {
            	logger.info("提示：操作详情detail长度:" + detail.length() + ",超过最大长度,需要截取!");
            	logger.info(detail);//如果日志要被截取那么先截取到 将所有日志输出到日志文件当中
                detail = detail.substring(0, MAX_LENTH_DETAIL);
            }
            
            
            operLog.setDetail(detail);
            operLog.setIp(RequestUtil.getIp(request));
            operLog.setModuleName(moduleName);
            operLog.setCompName(compName);
            getOperLogService().save(operLog);
        } catch (Exception e) {
            logger.error("保存操作日志报错", e);
        }
    }
    
    

//    /**
//     * 保存操作日志 针对app第三方调用.
//     *
//     * @param request request
//     * @param moduleName 操作模块
//     * @param compName 操作对象
//     * @param detail 日志详情
//     * @author dozen.zhang
//     * @date 2017年6月22日 下午8:02:04
//     */
//    public static void add4App(HttpServletRequest request, String moduleName, String compName, String detail) {
//        try {
//            OperLog operLog = new OperLog();
//            try {
//                SessionApp app = SessionUtil.getCurSessionApp(request);
//                if (app != null) {
//                    operLog.setUserId(app.getId());
//                    operLog.setUserName(app.getAppId());
//                }
//            } catch (Exception e) {
//                logger.error("取不到app信息", e);
//            }
//            if (StringUtil.isNotBlank(detail) && detail.length() > MAX_LENTH_DETAIL) {
//                logger.warn("提示：操作详情detail长度:" + detail.length() + ",超过最大长度,需要截取!");
//                detail = detail.substring(0, MAX_LENTH_DETAIL);
//            }
//            operLog.setDetail(detail);
//            operLog.setIp(IPUtil.getIpAddr(request));
//            operLog.setModuleName(moduleName);
//            operLog.setCompName(compName);
//            getOperLogService().save(operLog);
//        } catch (Exception e) {
//            logger.error("保存操作日志报错", e);
//        }
//    }
//
//

    /**
     * 获取操作日志服务
     * 
     * @return OperLogService
     * @author dozen.zhang
     * @date 2017年6月22日 下午8:02:04
     */
    private static OperLogService getOperLogService() {
        if (operLogService == null) {
            operLogService = (OperLogService) BeanUtil.getBean("operLogService");
        }
        return operLogService;
    }
}
