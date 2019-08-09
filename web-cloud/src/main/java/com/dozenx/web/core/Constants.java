package com.dozenx.web.core;

/**
 * Created by dozen.zhang on 2016/9/30.
 */
public class Constants {
    public static final String tempRootpath = System.getProperty("user.dir") + "/temp/";

    public static final int excelPageSize=1000;

    public static final  String suffix=".html";

    public static final String excelext=".xls";

    public static final String exportexcel="exportexcel";//是否是导出操作的key

    public static final String dataUpdate="更新";

    public static final String dataSave="保存";

    public static final String dataDelete="删除";

    public static final String cacheKey="springraincache";

    public static final String qxCacheKey="springrainqxcache";

    public static final String tableExt="_history_";

    public static final String frameTableAlias="frameTableAlias";

    public static final String pageurlName="pageurlName";

    public static final String returnDatas="returnDatas";

    public static final String SESSION_DTO = "SESSION_DTO";
    /**对应的是一个sessionUser**/
    public static final String SESSION_USER = "SESSION_USER";
    /**对应的是一个角色code 数组**/
    public static final String SESSION_ROLES = "SESSION_ROLES";
    /**对应的是一个SysPermission 数组**/
    public static final String SESSION_PERMISSIONS = "SESSION_PERMISSIONS";

    public static final String SESSION_MENUS = "SESSION_MENUS";
    public static final String SESSION_MERCHANT = "AWIFI_MERCHANT";
    public static final String ROLE_MERCHANT_MANAGER = "1";
    public static final String ROLE_MERCHANT_AGENT_MANAGER = "2";
    // 用户对应的手机号对应的设备mac 设备deviceId 用户mac
    public static final String REDIS_TEL_TO_APMACTERMACDEVID = "msp_8";
    // 临时放行次数 手机号-放通次数
    public static final String REDIS_TEMP_PASS_TIMES = "msp_9";
    // public static String REDIS_KEY_TEMPORARY_PASS="msp_9";
    //过期时间
    public static final String REDIS_TIME_TO_DEVPARAM = "msp_zdd_8";

    public static final String REDIS_TEL_TO_TERMAC_MER = "user_device";


    public static final String REDIS_PKG_GET="MSP-free_pkg_get_auth";
    //认证
    //public static final String reloginsession="shiro-reloginsession";
    //认证
    public static final String authenticationCacheName="shiro-authenticationCacheName";
    //授权
    public static final String authorizationCacheName="shiro-authorizationCacheName";
    //realm名称
    public static final String authorizingRealmName="shiroDbAuthorizingRealmName";

    //缓存用户最后有效的登陆sessionId
    public static final String keeponeCacheName="shiro-keepone-session";


    /**
     * 默认验证码参数名称
     */
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    /**
     * 登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key默认名称
     */
    public static final String DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE = "showCaptcha";

    /**
     * 默认在session中存储的登录次数名称
     */
    public static final String DEFAULT_LOGIN_NUM_KEY_ATTRIBUTE = "loginNum";
    //允许登录次数，当登录次数大于该数值时，会在页面中显示验证码
    public static final Integer allowLoginNum=1;



    /** 问卷调查中消费价格 大于0小于0.4  计数*/
    public static final String REDIS_QUESTIONNAIRE_LOW = "REDIS_QUESTIONNAIRE_LOW";
    /** 问卷调查中消费价格 大于等于0.4小于等于0.8  计数*/
    public static final String REDIS_QUESTIONNAIRE_MIDDEL = "REDIS_QUESTIONNAIRE_MIDDEL";
    public static Long QUESTIONNAIRE_RED_PACKAGE = 10086L;
    public static String CHARSET ="utf-8";

    //连接超时时间
    public static int HTTP_CONNECT_TIME_OUT =5000;
    //读取超时时间
    public  static int HTTP_READ_TIME_OUT = 5000;

    public static final String APPLICATION_JSON="application/json";

    public static final String WEBROOT="";
}
