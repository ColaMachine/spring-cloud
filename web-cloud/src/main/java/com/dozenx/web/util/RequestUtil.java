package com.dozenx.web.util;

import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RequestUtil {
	
	
	 /**
	  * 是否为Ajax请求
	  * @author syf
	  * @creationDate. 2015年5月29日 下午2:48:57 
	  * @param request
	  * @return
	  */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		//System.out.println(request.getContentType());
		String contentType = request.getContentType();
		if(StringUtil.isNotBlank(contentType ) && contentType .indexOf("application/json")!=-1){
			return true;
		}
		return (request.getHeader("x-requested-with") == null) ? false : true;
	}
	/**
	 * 把map的值放回到request里
	 * @param map
	 * @param request
	 * @author 张智威
	 * @date 2017年4月11日 下午10:01:47
	 */
	public static void push2Request(Map map,HttpServletRequest request){
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
//			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
//			request.setAttribute((String)entry.getKey(),entry.getValue());
			String key = (String)it.next();
			Object value=map.get(key);
			request.setAttribute(key,value);
		}
	}

	/**
	 * 获取request的ip
	 * @param request
	 * @return
     */
	public static String getIp(HttpServletRequest request){
		String ip = request.getRemoteAddr();
		if(StringUtil.isBlank(ip)){
			ip = request.getHeader("x-forwarded-for");
		}
		return ip;

	}
	/**
	 * 向客户端输出
	 * @author syf
	 * @creationDate. 2015年5月29日 下午2:55:08 
	 * @param response
	 * @param outObj
	 * @param outEncoding
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, Object outObj, String outEncoding) throws IOException {
		response.setCharacterEncoding(outEncoding);
		PrintWriter out = null;
		out = response.getWriter();
		out.print(outObj);
		out.flush();
		out.close();
	}
	
	/**
	 * 从request中获取page
	 * @param request
	 * @return
	 * @author dozen.zhang
	 */
	public static Page getPage(HttpServletRequest request){
	    String curPage = request.getParameter("curPage");
	    String pageSize = request.getParameter("pageSize");
		/*if(StringUtil.isBlank(curPage)||StringUtil.isBlank(pageSize) ){
			return null;
		}*/
		if(StringUtil.isBlank(curPage)){
			curPage ="1";
		}
		if(StringUtil.isBlank(pageSize)){
			pageSize ="10";
		}
	    Page page =new Page();
	    page.setCurPage(Integer.valueOf(curPage));
	    page.setPageSize(Integer.valueOf(pageSize));
	    return page;
	}

	public static Page getPage(Map params){

		/*if(StringUtil.isBlank(curPage)||StringUtil.isBlank(pageSize) ){
			return null;
		}*/
		int curPage = MapUtils.getInteger(params,"curPage",1);
		int pageSize = MapUtils.getInteger(params,"pageSize",10);

		Page page =new Page();
		page.setCurPage(curPage);
		page.setPageSize(pageSize);
		return page;
	}

	/**
	 * 获取终端类型
	 * @param userAgent 请求头里面的userAgent
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 上午10:58:28
	 */
	public static String getTerminalType(String userAgent){
		try{
			if(StringUtil.isBlank(userAgent)){
				return StringUtil.EMPTY;
			}
			String userAgentLC = userAgent.toLowerCase();//小写
			if(userAgentLC.indexOf("ipad") != -1){
				return "ipad";
			}else if(userAgentLC.indexOf("ipod") != -1){
				return "ipod";
			}else if(userAgentLC.indexOf("windows phone") != -1){
				return "windows phone";
			}else if(userAgentLC.indexOf("iphone") != -1){
				return "iphone";
			}else if(userAgentLC.indexOf("kindle") != -1){
				return "kindle";
			}else if(userAgentLC.indexOf("silk") != -1){
				return "silk";
			}else if(userAgentLC.indexOf("android") != -1){
				return "android";
			}else if(userAgentLC.indexOf("win") != -1){
				return "win";
			}else if(userAgentLC.indexOf("mac") != -1){
				return "mac";
			}else if(userAgentLC.indexOf("linux") != -1){
				return "linux";
			}else if(userAgentLC.indexOf("cros") != -1){
				return "cros";
			}else if(userAgentLC.indexOf("playbook") != -1){
				return "playbook";
			}else if(userAgentLC.indexOf("bb") != -1){
				return "bb";
			}else if(userAgentLC.indexOf("blackberry") != -1){
				return "blackberry";
			}else{
				return "other";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return StringUtil.EMPTY;
	}

	/**
	 * 获取终端品牌
	 * @param userAgent 请求头里面的userAgent
	 * @param terminalType 终端类型
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 上午10:58:28
	 */
	public static String getTerminalBrand(String userAgent, String terminalType){
		try{
			if(StringUtil.isBlank(terminalType)){
				return StringUtil.EMPTY;
			}
			String userAgentLC = userAgent.toLowerCase();//小写
			if(terminalType.equals("iphone")){
				return getTerminalBrandForIphone(userAgentLC);
			}else if(terminalType.equals("android")){
				return getTerminalBrandForAndroid(userAgentLC);
			}else{
				return terminalType;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return StringUtil.EMPTY;
	}

	/**
	 * 获取终端品牌-iphone
	 * @param userAgent 请求头里面的userAgent
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 上午10:58:28
	 */
	private static String getTerminalBrandForIphone(String userAgent){
		Pattern p = Pattern.compile("cpu([\\w\\- ]+)like");
		Matcher  m = p.matcher(userAgent);
		String terminalBrand = null;
		while(m.find()){
			terminalBrand =  m.group(1);
		}
		if(StringUtil.isBlank(terminalBrand)){
			return StringUtil.EMPTY;
		}
		return terminalBrand.trim().replaceAll("_", ".");
	}

	/**
	 * 获取终端品牌-android
	 * @param userAgent 请求头里面的userAgent
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 上午10:58:28
	 */
	private static String getTerminalBrandForAndroid(String userAgent){
		Pattern p = Pattern.compile(";([\\w\\- ]+)build\\/");
		Matcher  m = p.matcher(userAgent);
		String terminalBrand = null;
		while(m.find()){
			terminalBrand =  m.group(1);
		}
		if(StringUtil.isBlank(terminalBrand)){
			return StringUtil.EMPTY;
		}
		return terminalBrand.trim().replaceAll("_", ".");
	}

	/**
	 * 获取终端版本
	 * @param userAgent 请求头里面的userAgent
	 * @param terminalType 终端类型
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 下午2:28:32
	 */
	public static String getTerminalVersion(String userAgent, String terminalType){
		try{
			if(StringUtil.isBlank(terminalType)){
				return StringUtil.EMPTY;
			}
			String userAgentLC = userAgent.toLowerCase();//小写
			if(terminalType.equals("win")){
				return getTerminalVersionForWin(userAgentLC);
			}else if(terminalType.equals("mac")){
				return getTerminalVersionForMac(userAgentLC);
			}else if(terminalType.equals("iphone") || terminalType.equals("ipad")){
				return getTerminalVersionForIphone(userAgentLC);
			}else if(terminalType.equals("android")){
				return getTerminalVersionForAndroid(userAgentLC);
			}else{
				return StringUtil.EMPTY;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return StringUtil.EMPTY;
	}
	/**
	 * 获取终端版本-win
	 * @param userAgent 请求头里面的userAgent
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 下午2:28:32
	 */
	private static String getTerminalVersionForWin(String userAgent){
		Pattern p = Pattern.compile("windows([\\w\\-\\. ]+)");
		Matcher  m = p.matcher(userAgent);
		String terminalVersion=null;
		while(m.find()){
			terminalVersion=m.group(0);
		}
		if(StringUtil.isBlank(terminalVersion)){
			return StringUtil.EMPTY;
		}
		return getTerminalVersionNum(terminalVersion);
	}

	/**
	 * 获取终端版本-mac
	 * @param userAgent 请求头里面的userAgent
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 下午2:28:32
	 */
	private static String getTerminalVersionForMac(String userAgent){
		Pattern p = Pattern.compile("macintosh([;\\w\\-\\. ]+)");
		Matcher  m = p.matcher(userAgent);
		String terminalVersion=null;
		while(m.find()){
			terminalVersion=m.group(0);
		}
		if(StringUtil.isBlank(terminalVersion)){
			return StringUtil.EMPTY;
		}
		return getTerminalVersionNum(terminalVersion);
	}

	/**
	 * 获取终端版本-iphone
	 * @param userAgent 请求头里面的userAgent
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 下午2:28:32
	 */
	private static String getTerminalVersionForIphone(String userAgent){
		Pattern p = Pattern.compile("(iphone|ipad)([;\\w\\-\\. ]+)");
		Matcher  m = p.matcher(userAgent);
		String terminalVersion=null;
		while(m.find()){
			terminalVersion=m.group(2);
		}
		if(StringUtil.isBlank(terminalVersion)){
			return StringUtil.EMPTY;
		}
		return getTerminalVersionNum(terminalVersion);
	}

	/**
	 * 获取终端版本-iphone
	 * @param userAgent 请求头里面的userAgent
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 下午2:28:32
	 */
	private static String getTerminalVersionForAndroid(String userAgent){
		Pattern p = Pattern.compile("linux([;\\w\\-\\. ]+)");
		Matcher  m = p.matcher(userAgent);
		String terminalVersion=null;
		while(m.find()){
			terminalVersion=m.group(0);
		}
		if(StringUtil.isBlank(terminalVersion)){
			return StringUtil.EMPTY;
		}
		return getTerminalVersionNum(terminalVersion);
	}

	/**
	 * 获取终端版本-具体数字
	 * @param terminalVersion 终端版本，需要进一步截取
	 * @return 终端类型
	 * @author 许小满
	 * @date 2016年11月24日 下午2:28:32
	 */
	private static String getTerminalVersionNum(String terminalVersion){
		Pattern p = Pattern.compile("[\\d_\\-\\.]+");
		Matcher  m = p.matcher(terminalVersion);
		String terminalVersionNum = null;
		while(m.find()){
			terminalVersionNum = m.group(0);
			break;
		}
		if(StringUtil.isBlank(terminalVersionNum)){
			return StringUtil.EMPTY;
		}
		return terminalVersionNum.trim().replaceAll("_", ".");
	}

	/**
	 * 测试
	 * @param args 参数
	 * @author 许小满
	 * @date 2016年11月24日 上午10:50:05
	 */
	public static void main(String[] args) {
		String[] userAgents = {
				// PC端
				"mozilla/5.0 (macintosh; intel mac os x 10_12_1) applewebkit/537.36 (khtml, like gecko) chrome/54.0.2840.71 safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36",
				"mozilla/5.0 (x11; linux x86_64) applewebkit/537.36 (khtml, like gecko) chrome/53.0.2785.143 safari/537.36",
				// 手机端
				"mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko) version/9.0 mobile/13b143 safari/601.1",
				"Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
				"mozilla/5.0 (linux; android 6.0; nexus 5 build/mra58n) applewebkit/537.36 (khtml, like gecko) chrome/48.0.2564.23 mobile safari/537.36",
				"mozilla/5.0 (linux; android 5.0; sm-g900p build/lrx21t) applewebkit/537.36 (khtml, like gecko) chrome/48.0.2564.23 mobile safari/537.36",
				"mozilla/5.0 (linux; u; android 4.4.4; zh-cn; hm note 1s build/ktu84p) applewebkit/537.36 (khtml, like gecko) version/4.0 chrome/46.0.2490.85 mobile safari/537.36 xiaomi/miuibrowser/8.4.3",
		};

		for(String userAgent : userAgents){
			System.out.println(userAgent);
			String terminalType = getTerminalType(userAgent);
			System.out.println("终端类型：" + terminalType);

			String terminalBrand = getTerminalBrand(userAgent, terminalType);
			System.out.println("终端品牌：" + terminalBrand);

			String terminalVersion = getTerminalVersion(userAgent, terminalType);
			System.out.println("终端版本：" + terminalVersion);
		}
	}

	/**
	 * 从request中提取参数转成hashmap
	 * @param request
	 * @return
     */
	public static HashMap request2Map(HttpServletRequest request) {
		HashMap map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					// System.out.println("参数：" + paramName + "=" + paramValue);
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}


	/**
	 * 从request中获取page
	 * @param request
	 * @return
	 * @author dozen.zhang
	 */
	public static Page getPage(HttpServletRequest request, Map<String, Object> paramMap) {
		String curPage = MapUtils.getString(paramMap, "curPage");
		String pageSize = MapUtils.getString(paramMap, "pageSize");
		if (StringUtil.isBlank(curPage) && StringUtil.isBlank(pageSize)) {
			curPage = request.getParameter("curPage");
			pageSize = request.getParameter("pageSize");
		}
		if (StringUtil.isBlank(curPage)) {
			curPage = "1";
		}
		if (StringUtil.isBlank(pageSize)) {
			pageSize = "10";
		}
		Page page = new Page();
		page.setCurPage(Integer.valueOf(curPage));
		page.setPageSize(Integer.valueOf(pageSize));
		return page;
	}


}