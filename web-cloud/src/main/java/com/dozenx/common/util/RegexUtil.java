package com.dozenx.common.util;


//import com.dozenx.web.core.log.ResultDTO;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import com.dozenx.common.exception.ValidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
//	public static ResultDTO email(String email){
//		if(email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
//			return  ResultUtil.getSuccResult();
//		}else{
//			return ResultUtil.getWrongResultFromCfg("err.email.format");
//		}
//	}
//
//	/**
//	 * 说明:判断是否正确
//	 * @param pwd
//	 * @return
//	 * @author dozen.zhang
//	 * @date 2015年5月20日下午5:00:33
//	 */
//	public static ResultDTO pwd(String pwd){
//		if(StringUtil.isBlank(pwd)  ){
//			return ResultUtil.getWrongResultFromCfg("err.pwd.empty");
//		}else if(pwd.length() <6 || pwd.length()>15){
//			return ResultUtil.getWrongResultFromCfg("err.email.leng");
//		}
//		 return ResultUtil.getSuccResult();
//	}
//
//	/**
//	 * 正则匹配
//	 * @param str 待匹配字符串
//	 * @param pattern 正则表达式
//	 * @return true 成功、false 失败
//	 * @author 许小满
//	 * @date 2017年1月17日 下午2:17:25
//	 */
	public static boolean match(String str, String pattern){
		return match(str, pattern, null);
	}
//
//	/**
//	 * 正则匹配
//	 * @param str 待匹配字符串
//	 * @param pattern 正则表达式
//	 * @param flags flags
//	 * @return true 成功、false 失败
//	 * @author 许小满
//	 * @date 2016年7月25日 下午12:13:16
//	 */
	public static boolean match(String str, String pattern, Integer flags){
		if(str == null){
			return false;
		}
		Pattern p = null;
		if(flags == null){
			p = Pattern.compile(pattern);
		}else {
			p = Pattern.compile(pattern, flags);
		}
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static void main(String[] args) {
		String ip = "5.01";

		System.out.println(match(ip, "[0-9]{1,3}(\\.[0-9]{1,2})?"));
	}


	public static void regex(String key, Object value, String pattern,String msg) {
		if(value != null) {
			String valueStr = (String)value;
			if(!match(valueStr, pattern)) {
				throw new ValidException("30202016", msg);
			}
		}
	}
	
}
