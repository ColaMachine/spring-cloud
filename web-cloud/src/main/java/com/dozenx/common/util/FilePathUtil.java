package com.dozenx.common.util;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.StringUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class FilePathUtil {
	
	/**
	 * 获取项目classpath目录的绝对路径
	 * @return classes目录的绝对路径<br/>
	 * 	file:/F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/
	 */
	public static String getAbsolutePathWithClass() {
		String path = FilePathUtil.class.getResource("/").getPath();
		try {
			//获得的路径是getResource方法使用了utf-8对路径信息进行了编码。
			//当路径中存在中文和空格时，他会对这些字符进行转换。
			path = java.net.URLDecoder.decode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(path.length() > 5 && "file:".equals(path.substring(0,5))){
			//windows 路径格式 file:/F:/home/myweb/cms/cms/
			//linux 路径 格式 file:/home/myweb/cms/cms/
			path = path.substring(5);
		}
		return path;
	}
	
	/**
	 * 获得web项目的根路径
	 * @return
	 */
	public static String getWEBRoot(){
		String path = FilePathUtil.getAbsolutePathWithClass().toString();
		if(path.indexOf("WEB-INF") > -1){
			path = path.substring(0,path.indexOf("WEB-INF"));
		}
		return path;
	}

	/**
	 * 获取项目classPath目录下的指定目录的绝对路径
	 * @param path
	 * 			classes目录下的指定目录.比如:/com/
	 * @return file:/F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/com/
	 */
	public static URL getAbsolutePathWithClass(String path) {
		return FilePathUtil.class.getResource(path);
	}
	 
	/**
	 * 获取指定类文件的所在目录的绝对路径
	 * @param clazz
	 * @return 类文件的绝对路径.例如:<br/> 包com.Aries.Util.Web下的Main.java类.<br/>
	 *  路径为:file:/
	 *         F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/com/Aries/Util/Web/
	 */
	public static String getAbsolutePathWithClass(Class<?> clazz) {
		String path = clazz.getResource("").toString();
		if(path.length() > 5 && "file:".equals(path.substring(0,5))){
			//windows 路径格式 file:/F:/home/myweb/cms/cms/
			//linux 路径 格式 file:/home/myweb/cms/cms/
			path = path.substring(5);
		}
		return path;
	}
	public static String joinPath(String prefix ,String affix){
		if((!prefix.endsWith("/")) && (!prefix.endsWith("\\"))){
			prefix+="/";
		}
		if(com.dozenx.common.util.StringUtil.isNotBlank(affix) && affix.startsWith("/")){
			affix=affix.substring(1);
		}
		return prefix+affix;
	}
	//修复 路径可能为null的错误
	public static String joinPath(String prefix ,String seperator,String ... affix){
		for(String str: affix){
			if(com.dozenx.common.util.StringUtil.isBlank(str)){
				continue;
			}
			if(!prefix.endsWith(seperator)){
				prefix+=seperator;
			}
			if(StringUtil.isNotBlank(str) && str.startsWith(seperator)){
				str=str.substring(1);
			}
			prefix+=str;

		}

		return prefix;
	}
	public static String getYMDPathAffix(String path){

		return joinPath(path, com.dozenx.common.util.DateUtil.toDateStr(new java.util.Date(),"yyyy/MM/dd/"));
	}
	public static String getYMDPathAffix(){
		String ymdStr = DateUtil.toDateStr(new java.util.Date(),"yyyy/MM/dd");
		return ymdStr;
	}
	
	public static void main(String[] args) {
		System.out.println(getWEBRoot());
	}
}
