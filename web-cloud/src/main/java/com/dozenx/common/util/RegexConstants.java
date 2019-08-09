package com.dozenx.common.util;

import java.util.regex.Pattern;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 下午5:18:59
 * 创建作者：亢燕翔
 * 文件名称：regexConstants.java
 * 版本：  v1.0
 * 功能：  正则常量
 * 修改记录：
 */
public class RegexConstants {

    /** 数字 */
    public static final String NUMBER_PATTERN = "^-?\\d+$";
    /**ip地址*/
    public static final String IP_PATTERN = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
    /**ip地址*/
    public static final Pattern IP_PATTERN_COMPILE = Pattern.compile(IP_PATTERN);
    /** MAC地址 */
    public static final String MAC_PATTERN = "^[0-9A-F]{12}$";
    /** MAC地址，支持大小写 */
    public static final String MAC_PATTERN_LOWER = "^[0-9a-fA-F]{12}$";
    /** MAC地址正确格式中文说明 */
    public static final String MAC_PATTERN_DSP = "12位字符，包含A-F、0-9";
    /**商户名称正则   约束统一*/
    public static final String MERCHANT_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**账号正则*/
    public static final String USER_NAME_PATTERN = "^[0-9a-zA-Z-_]{1,50}$";
    /**热点名称正则(aWiFi- 区分大小写)*/
    public static final String SSID_NAME_PATTERN = "^aWiFi-([0-9a-zA-Z-_])+";
    /**静态用户名正则*/
    public static final String STATIC_USER_NAME = "^[0-9a-zA-Z-_]{1,50}$";
    /**静态用户密码正则*/
    public static final String PASSWORD = "^[0-9a-zA-Z_@$-]{1,50}$";
    /**真实姓名正则 允许为空，正则校验[1-20位字符，包括字母、汉字]*/
    public static final String REAL_NAME = "^[a-zA-Z\u4e00-\u9fa5]{1,20}$";
    /**部门正则 允许为空，正则校验[1-20位汉字、字母、数字]*/
    public static final String DEPT_NAME = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,20}$";
    /** SC|WIC开头的，不区分大小写(特通账号) */
    public static final String SC_PATTERN = "^(sc|wic|brics).*";
    /**1开头的11位符合手机号码规则的数字*/
    public static final String CELLPHONE = "^(1[0-9]{10})?$";
    /**护照正则*/
    public static final String PASSPORT = "^[0-9a-zA-Z]{1,20}$";
    /**身份证号正则*/
    public static final String IDENTITY_CARD= "^[0-9]{17}([0-9]|X){1}$";
    /** 邮箱 */
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"; 
    /**闲时下线时间,单位小时,小数点后保留一位0或5*/
    public static final String HOUR_PATTERN = "^\\d+(\\.[0|5])?$";
    /**项目名称正则*/
    public static final String PROJECT_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5_]{1,50}$";
    /**组件名称正则*/
    public static final String COMPONENT_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**站点名称正则*/
    public static final String SITE_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5_]{1,50}$";
    /**策略名称正则*/
    public static final String STRATEGY_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5_]{1,50}$";
    /**管理员部门正则 允许为空，正则校验[1-50位汉字、字母、数字]*/
    public static final String DEPT_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**管理员联系人正则*/
    public static final String CONTACT_PERSON_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**管理员联系方式正则*/
    public static final String CONTACT_WAY_PATTERN = "^[0-9-,]{1,50}$";
    /**详细地址正则*/
    public static final String ADDRESS_PATTER = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,100}$";
    /** SSID(前缀)  */
    public static final String SSID_PRIEFIX = "^[0-9a-zA-Z]{0,4}$";
    /** SSID(后缀)  */
    public static final String SSID_SUFFIX = "^[0-9a-zA-Z]{1,5}$";
    /** 定制终端升级包版本 */
    public static final String UPGRADETPATCH_VERSION = "([a-z]||[A-Z]){1-17}[0-9].[0-9].[0-9]";
    /** 域名校验  */
    public static final String URL = "([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /** MAC地址 */
    public static final String MAC_NO_CASE_PATTERN = "^[0-9a-fA-F]{12}$";
    /** 定制终端版本号校验 */
    public static final String FATAP_VERSION = "(V|v)[0-z]{1,9}\\\\.[0-z]{1,9}\\\\.[0-z]{1,9}";
    /** 0/1校验 */
    public static final String ZERO_OR_ONE="0|1";
    /**设备纬度校验*/    
    public static final String DEVICE_LATITUDE = "(-|\\+)?(90\\.0{0,6}|(\\d|[1-8]\\d)\\.\\d{0,6})";
    /**设备经度校验*/
    public static final String DEVICE_LONGITUDE =  "(-|\\+)?(180\\.0{0,6}|(\\d{1,2}|1([0-7]\\d))\\.\\d{0,6})";
    /**型号名称校验*/
    public static final String MODEL_NAME = "^[^#%*@&]{1,100}$";
    /**站点页html组件信息匹配正则*/
    public static final String SITE_PAGE_THML_REPLACE_STRING = "(<div class=\"awifi-container\">)(.+?)(</div></div>)";
    /**公司名称正则   约束统一*/
    public static final String COMPANY_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**公共正则  100位以内字符，不包括#，%，*，@，&特殊字符 */
    public static final String UNIFORM_PATTERN = "^[^#%*@&]{0,100}$";
    /** awifi热点导入使用----开始 */
    /**awifi热点导入中热点名称,中文字母数字开头,长度70以内*/
    public static final String AWIFI_HOT_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,70}$";
    /**30以内字符*/
    public static final String STRING_30_PATTERN = "^.{1,30}$";
    /**50以内字符*/
    public static final String STRING_50_PATTERN = "^.{1,50}$";
    /**100以内字符*/
    public static final String STRING_100_PATTERN = "^.{1,100}$";
    /**160以内字符*/
    public static final String STRING_160_PATTERN = "^.{1,160}$";
    /** 整形数字字符串,长度小于10 */
    public static final String NUM_STRING_10_PATTER = "^(0|[1-9][0-9]{0,9})$";
    /** 经度 -180.0~+180.0 */
    public static final String XPOS_PATTERN = "^$|(-|\\+)?(180|180\\.0|(\\d{1,2}|1([0-7]\\d))\\.\\d|(\\d{1,2}|1([0-7]\\d)))";
    /** 纬度 -90.0~+90.0之间 */
    public static final String YPOS_PATTERN = "^$|(-|\\+)?(90|90\\.0|(\\d|[1-8]\\d)\\.\\d|(\\d|[1-8]\\d))";
    /** 热点等级:ABCD */
    public static final String HOT_DEGREE_PATTERN = "^[ABCD]{1}$";
    /**端口 00-65535*/
    public static final String PORT_PATTERN = "^([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])$";
    /** 11位手机号 座机号 */
    public static final String PHONE_PATTERN = "(^(\\d{3,4}-)?\\d{7,8})$|(1[3|5|7|8]\\d{9})";
    /** awifi热点导入使用----结束 */
    
    /** 厂家名称*/
    public static final String CPOR_NAME_PATTERN="^[^#%*@&]{1,100}$";
    /**联系人*/
    public static final String CONTACTER_PATTERN ="^?|([^#%*@&]{1,100})$";
    /**联系方式*/
    public static final String CONTACTWAY_PATTERN ="^?|([^#%*@&]{1,100})$";
    /**邮箱*/
    public static final String EMAIL_SIMPLE_PATTERN ="^[^#%*&]{1,30}$";
    
    /**防蹭网码*/
    public static final String NETDEF_CODE_PATTERN ="^[0-9a-zA-Z]{4}$";
    
    /**截取/media/picture图片路径*/
    public static final String MEDIA_PICTURE_PATTERN = "\"(/media/picture/)(.+?)\"";

    /**账号正则*/
    public static final String ALPHA_NUMBER_PATTERN = "^[0-9a-zA-Z]+$";

    /**中文数字字母*/
    public static final String ZHONGWEN_ALPHA_NUMBER_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,70}$";


    public static final String MONEY_PATTERN = "^[0-9]{1,3}(\\.[0-9]{1,2})?$";


    /**
     * 正则字符串长度在start-end之间
     * @param start 正则长度开始
     * @param end 正则长度结束
     * 创建日期:2017年7月19日 上午9:51:59
     * 创建作者：许尚敏
     * @return 正则内容
     */
    public static String getStringPattern(int start, int end){
        String pattern = "^.{" + start + "," + end + "}$";
        return pattern;
    }
    
    
//    public static void main(String[] args) {
//        System.out.println(Pattern.matches(SSID_NAME_PATTERN, "aWiFi-xxxx"));
//        System.out.println(Pattern.matches(SSID_NAME_PATTERN, "aWiFi-呵呵"));
////        System.out.println(Pattern.matches("^?|([^#%*@&]{0,100})$", "aa "));
//        
//    }
}