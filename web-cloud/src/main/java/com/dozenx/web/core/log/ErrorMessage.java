package com.dozenx.web.core.log;

import com.dozenx.common.Path.PathManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * 系统错误信息实现类
 *
 * @author
 */
public class ErrorMessage {
    private static final Logger logger = LoggerFactory.getLogger(ErrorMessage.class);
/*	*//** 系统错误 **//*
    public static final int SYSTEM=1;
	*//** 应用级别错误(前端参数错误) **//*
	public static final int PARAM=2;
	*//** 业务级别错误(service自身处理出错) **//*
	public static final int SERVICE=3;
	*//** 依赖级别错误(service调用第三方服务出错) **//*
	public static final int THIRD=4;
	*//** 交互级别错误(正常业务逻辑,非错误,需要通知用户,如角色名重复) **//*
	public static final int NOTIFI=5;
	*/
    /**
     * 未知异常
     **//*
	public static final int EXCEPTION=99; */



    private static Properties msgProp;

    /**
     * 载入配置文件
     */
    private static void loadMsgProp() {
        if (msgProp != null) {
            return;
        }
        msgProp = new Properties();
        try {
            InputStream inputStream = PathManager.class.getResourceAsStream("/properties/message.properties");
            if (inputStream == null) {
                inputStream = PathManager.class.getResourceAsStream("/message.properties");
            }
            if (inputStream == null) {
                logger.error("未找到 not find message.properties");
                return;
            }
            msgProp.load(PathManager.class.getResourceAsStream("/properties/message.properties"));
        } catch (Exception e) {
            logger.error("", e);
//            e.printStackTrace();
        }
    }

    /**
     * 获取错误消息
     *
     * @param key
     * @return
     */
    public static String getErrorMsg(String key) {
        loadMsgProp();
        Object obj = msgProp.get(key + ".msg");
        if (obj != null) {
            return obj.toString();
        } else {
            return null;
        }
    }

    /**
     * 获取错误消息
     *
     * @param key
     * @return
     */
    public static String getErrorMsg(String key, Object suffix) {
        loadMsgProp();
        Object obj = msgProp.get(key + ".msg");
        if (obj != null) {
            return String.format(obj.toString(), suffix);
        } else {
            return null;
        }
    }

    /**
     * 获取错误消息
     *
     * @param key
     * @return
     */
    public static String getErrorMsg(String key, Object[] suffix) {
        loadMsgProp();
        Object obj = msgProp.get(key + ".msg");
        if (obj != null) {
            return String.format(obj.toString(), suffix);
        } else {
            return null;
        }
    }

    /**
     * 获取错误消息
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        loadMsgProp();
        Object obj = msgProp.get(key);
        if (obj != null) {
            return obj.toString();
        } else {
            return null;
        }
    }

    /**
     * 获取错误码
     *
     * @param key
     * @return
     */
    public static String getErrorMsgCode(String key) {
        loadMsgProp();
        Object obj = msgProp.get(key + ".code");
        if (obj != null) {
            return obj.toString();
        } else {
            return null;
        }
    }


    /**
     * ======================== 登录/注册模块 ===============================
     **/

    public static final String PASSWORD_ERROR = "密码错误！";
    public static final String USERNAME_PWD_ERROR = "用户名或密码错误！";


    /**
     * =========================== token =================================
     **/

    public static final String TOKEN_TIMEOUT = "token超时！";
    public static final String TOKEN_ERROR = "token错误！";


    /**
     * ======================= 个人资料管理模块 ==============================
     **/

    public static final String NEW_PASSWORD_ERROR = "新密码输入错误！";
    public static final String OLD_PASSWORD_ERROR = "原密码输入错误！";


    /**
     * ======================== 校验模块 ===================================
     **/

    public static final String RULE_CHECKBOX_ERROR = "不在可选范围内";
    public static final String RULE_LENGTH_ERROR = "长度不够或超出允许长度";
    public static final String RULE_NUMERIC_ERROR = "不是数字";
    public static final String RULE_REGEX_ERROR = "格式错误";
    public static final String RULE_REQUIRED_ERROR = "不能为空";
    public static final String RULE_NUMBER_RANGE = "的范围：";
    public static final String RULE_NUMBER_MIN = "的最小值：";


    /**
     * ========================== 应用模块 ================================
     **/

    public static final String APPLICATION_NAME_EXIST = "应用名称已存在！";
    public static final String APPLICATION_SHORT_NAME_EXIST = "应用简称已存在！";
    public static final String APPLICATION_NOT_EXIST = "应用不存在！";
    public static final String APPLICATION_TYPE_EXIST = "应用分类已存在！";
    public static final String APPLICATION_TYPE_HAS_APP = "该分类下有应用！";
    public static final String APPLICATION_TYPE_NOT_EXIST = "应用分类不存在！";
    public static final String IP_ILLEGAL = "不允许的IP地址";
    public static final String APP_NOT_SUITE_MERCHANT = "应用不存在或不适合当前商户开通！";
    public static final String MERCHANT_HAD_APP = "您已开通或停用该应用！";
    public static final String MERCHANT_NOT_USING_APP = "您尚未开通该应用！";
    public static final String MERCHANT_NOT_PAUSE_APP = "您没有停用该应用！";

    public static final String APPLICATION_MODIFY_EXIST = "该应用已变更！";
    public static final String APPLICATION_KEY_NOT_EXIST = "未找到appkey所对应的应用！";
    public static final String APPLICATION_PUBLISHED_READY = "该应用已发布！";
    public static final String APPLICATION_STATUE_ERROR = "应用状态错误！";
    public static final String APPLICATION_UNPUBLISH_READY = "该应用已下架！";
    public static final String APPLICATION_APP_ID_ERROR = "appId异常！";
    public static final String APPLICATION_APP_MODIFY_READY = "该应用已经产生变更记录，无法再次提交！";
    public static final String APPLICATION_APP_TAG_NOEXIST = "应用标签不存在！";
    public static final String APPLICATION_APP_TAG_QUOTE = "标签已被应用程序引用！";
    public static final String APPLICATION_APP_TAG_NODEL = "此标签不能删除！";
    public static final String APPLICATION_DATAINTREFACE_NULL = "数据接口不存在";
    public static final String APPLICATION_TAG_NOT_EXIST = "缺少应用标签(Portal)！";

    public static final String MERCHANT_NOT_EXIST = "商户不存在!";
    public static final String MERCHANT_CRMID_EXIST = "CRM ID已存在！";


    /**
     * ======================== 用户账号管理模块 =============================
     **/

    public static final String ACCOUNT_USERNAME_EXIST = "合作者账号已存在！";
    public static final String ACCOUNT_NICKNAME_EXIST = "合作者简称已存在！";
    public static final String ACCOUNT_REAL_NAME_EXIST = "合作者名称已存在！";
    public static final String ACCOUNT_AUDIT_RETRY_FOUR = "您已第四次提交了同一申请，您还有一次机会，若最后一次还未通过审核，系统将关闭该请求，关闭请求后您无法继续提交该请求，只能建立新的请求。";
    public static final String ACCOUNT_AUDIT_RETRY_OVER = "提交审核次数超出允许范围！";
    public static final String ACCOUNT_AUDIT_STATUS_CLOSE = "此用户已关闭！";
    public static final String ACCOUNT_NOT_EXIST = "此用户不存在！";


    /**
     * ======================== 管理员管理模块 ==============================
     **/

    public static final String ADMIN_USERNAME_EXIST = "管理员用户名已存在！";
    public static final String ADMIN_USERNAME_NOT_EXIST = "管理员用户名不存在！";
    public static final String ADMIN_TYPE_EXIST = "管理员角色已存在";
    public static final String ADMIN_TYPE_ALLOCATED = "管理员角色已分配";

    /**
     * ======================== 统计分析模块 ==============================
     **/

    public static final String DATE_FLAG_ERROR = "时间标识错误！";

    /**
     * ========================== 通用模块 ================================
     **/

    public static final String CITY_FORMAT_ERROR = "市格式错误！";
    public static final String CITY_NAME_ERROR = "市名称错误！";
    public static final String COUNTY_FORMAT_ERROR = "区县格式错误！";
    public static final String COUNTY_NAME_ERROR = "区县名称错误！";
    public static final String PROVINCE_FORMAT_ERROR = "省格式错误！";
    public static final String PROVINCE_NAME_ERROR = "省名称错误！";
    public static final String PAGE_NUMBER_ILLEGAL = "错误的页码！";
    public static final String NO_PERMISSION = "您没有操作权限！";
    public static final String NO_PERMISSION_ACCESS = "您没有访问权限！";
    public static final String REQUEST_ILLEGAL = "非法请求！";
    public static final String SYSTEM_EXCEPTION = "系统异常！异常编号：";
    public static final String INTERFACE_ERROR = "接口错误！";


}
