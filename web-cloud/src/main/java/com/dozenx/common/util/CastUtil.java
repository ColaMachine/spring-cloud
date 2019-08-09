package com.dozenx.common.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

//import org.apache.commons.lang3.StringUtil;
import com.dozenx.common.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:58:58
 * 创建作者：周颖
 * 文件名称：CastUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class CastUtil {
    
    /** 日志 */
    private static final Log logger = LogFactory.getLog(CastUtil.class);

    /**
     * list转string
     * @param list list
     * @param separator 分隔符
     * @return string
     * @author ZhouYing 
     * @date 2016年9月28日 下午2:30:03
     */
    @SuppressWarnings("rawtypes")
    public static String listToString(List list, char separator) {
       // return StringUtil.join(list.toArray(), separator);
        StringBuffer  s =new StringBuffer();
        for(int i=0;i<list.size();i++){
            s.append(list.get(i).toString());
            if(i==list.size()-1){
                s.append(separator);
            }
        }
        return s.toString();
    }
    
    /**
     * 将String数组转化为Long数组
     * @param strs String数组
     * @return Long数组
     * @author 许小满  
     * @date 2016年8月25日 上午8:14:18
     */
    public static Long[] toLongArray(String[] strs){
        int maxLength = strs != null ? strs.length : 0;
        if(maxLength <= 0){
            return null;
        }
        String str = null;
        Long[] longArray = new Long[maxLength];
        for(int i=0; i<maxLength; i++){
            str = strs[i];
            if(StringUtil.isBlank(str)){
                continue;
            }
            longArray[i] = Long.parseLong(str);
        }
        return longArray;
    }
    
    /**
     * 将String数组转化为Long数组
     * @param set long集合
     * @return Long数组
     * @author 许小满  
     * @date 2016年11月25日 上午10:37:46
     */
    public static Long[] toLongArray(Set<Long> set){
        int maxSize = set != null ? set.size() : 0;
        if(maxSize <= 0){
            return null;
        }
        Long[] longArray = new Long[maxSize];
        int i = 0;
        for(Long obj : set){
            if(obj == null){
                continue;
            }
            longArray[i] = obj;
            i++;
        }
        return longArray;
    }
    
    /**
     * 转String
     * @param obj 参数
     * @return String
     * @author 许小满  
     * @date 2017年5月27日 下午6:58:24
     */
    public static String toString(Object obj){
        if(obj == null){
            return null;
        }else if(obj instanceof String){//String
            return (String)obj;
        }else {//其它情况
            return obj.toString();
        }
    }
    /**
     * 转Integer
     * @param obj 参数
     * @return Integer
     * @author 周颖  
     * @date 2017年2月8日 下午7:37:19
     */
    public static Integer toInteger(Object obj){
        if(obj == null){
            return null;
        }
        else if(obj instanceof Integer){//Integer
            return (Integer)obj;
        }
        else if(obj instanceof Double){//
            return ((Double)obj).intValue();
        }
        else if(obj instanceof Long){
            return ((Long) obj).intValue();
        }
        else if(obj instanceof Float){
            return Math.round((float) obj);
        }
        else if(obj instanceof String){
            String str = obj.toString();
            if(StringUtil.isBlank(str)){
                return null;
            }
            return Integer.valueOf(str);
        }else{
            logger.debug("错误：数字转成Integer的数据类型超出了范围,只支持[Integer|Double|Long|String]!");
            return null;
        }
    }
    
    /**
     * 数字转成Long类型
     * @param obj 数字类型参数
     * @return Long 类型
     * @author 周颖  
     * @date 2017年2月7日 下午4:19:00
     */
    public static Long toLong(Object obj){
        if(obj == null){
            return null;
        }else if(obj instanceof Long){//Long
            return (Long) obj;
        }
        else if(obj instanceof Integer){//Integer
            return ((Integer) obj).longValue();
        }
        else if(obj instanceof Double){//Double
            return ((Double) obj).longValue();
        }
        else if(obj instanceof String){//String
            String str = (String)obj;
            if(StringUtil.isBlank(str)){
                return null;
            }
            return Long.parseLong(str);
        }else{
            logger.debug("错误：数字转成Long的数据类型超出了范围,只支持[Integer|Double|String]!");
            return null;
        }
    }
    
    /**
     * 数据转为Double
     * @param obj 源数据
     * @return double类型数据
     * @author 亢燕翔  
     * @date 2017年2月28日 下午6:32:07
     */
    public static Double toDouble(Object obj){
        if (obj == null) {
            return null; 
        } else if (obj instanceof Long || obj instanceof Integer || obj instanceof String || obj instanceof Float || obj instanceof Double || obj instanceof BigDecimal) {
            String str = obj.toString();
            if(StringUtil.isBlank(str)){
                return null; 
            }
            return Double.valueOf(str).doubleValue();
        } else {
            logger.debug("错误：数字转成Double的数据类型超出了范围,只支持[Long][Integer|Double|String][Float]!");
            return null;
        }
    }
    
    /**
     * 数据转为Float
     * @param obj 源数据
     * @return Float类型数据
     * @author 亢燕翔  
     * @date 2017年2月28日 下午6:57:22
     */
    public static Float toFloat(Object obj){
        if (obj == null) {
            return null; 
        } else if (obj instanceof Long || obj instanceof Integer || obj instanceof String || obj instanceof Float || obj instanceof Double) {
            String str = obj.toString();
            if(StringUtil.isBlank(str)){
                return null; 
            }
            return Float.valueOf(str).floatValue();
        } else {
            logger.debug("错误：数字转成Float的数据类型超出了范围,只支持[Long][Integer|Double|String][Float]!");
            return null;
        }
    }

    /**
     * 转成byte
     * @param obj 源数据
     * @return byte
     * @author 王冬冬  
     * @date 2017年5月8日 下午2:47:31
     */
    public static Byte toByte(Object obj) {
        if(obj == null){
            return null;
        }
        else if(obj instanceof Byte){//Byte
            return (Byte)obj;
        }
        else if(obj instanceof Integer){//Integer
            return ((Integer)obj).byteValue();
        }
        else if(obj instanceof String){
            String str = obj.toString();
            if(StringUtil.isBlank(str)){
                return null;
            }
            return Byte.valueOf(str);
        }else{
            logger.debug("错误：数字转成byte的数据类型出错");
            return null;
        }
    }
    
    /**
     * main
     * @param args 参数
     * @author 亢燕翔  
     * @date 2017年2月28日 下午6:57:48
     */
    //public static void main(String[] args) {
//        Float seconds = 2.5F * 60 * 60;
//        System.out.println(seconds);
        
//        System.out.println(toDouble(1L));
//        System.out.println(toDouble(2));
//        System.out.println(toDouble("3"));
//        System.out.println(toDouble(""));
//        System.out.println(toDouble(4.0F));
//        System.out.println(toDouble(5.0));
//        
//        System.out.println(toFloat(1L));
//        System.out.println(toFloat(2));
//        System.out.println(toFloat("3"));
//        System.out.println(toFloat(""));
//        System.out.println(toFloat(4.4F));
//        System.out.println(toFloat(5.5));
//        System.out.println(toInteger(2.5F));
    //}
    
}