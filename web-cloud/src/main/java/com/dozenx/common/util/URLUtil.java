package com.dozenx.common.util;

import java.net.URLEncoder;

public class URLUtil {
	
	/**
	 * 获取项目classpath目录的绝对路径
	 * @return classes目录的绝对路径<br/>
	 * 	file:/F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/
	 */
	public static String connact(String prefix,String affix) {
		//请

		String newUrl ="";
		if(prefix.endsWith("/")){//如果两个都是绝对路径
			if(affix.startsWith("/")){
				newUrl =prefix+affix.substring(1);
			}else{
				newUrl =prefix+affix;
			}
		}/*else if(prefix.startsWith("http://")){

			int index= prefix.indexOf("/",7);
			prefix= prefix.substring(0,index);
			newUrl = prefix+affix;
		}*/else{
			if(affix.startsWith("/")){
				newUrl =prefix+affix;
			}else{
				newUrl =prefix+"/"+affix;
			}
		}

		return newUrl;
	}
	public static void main(String args[]){
		String url ="nohup ./telnet>a.txt 2>1 &";
		System.out.println(URLEncoder.encode(url));
	}

}
