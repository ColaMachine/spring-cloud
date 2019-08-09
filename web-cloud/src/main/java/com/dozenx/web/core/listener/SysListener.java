package com.dozenx.web.core.listener;

import com.dozenx.common.config.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

public class SysListener extends HttpServlet implements ServletContextListener {
	/** 
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(SysListener.class);

	public void contextDestroyed(ServletContextEvent sce) {
		// 用于在容器关闭时,操作
	}

	// 用于在容器开启时,操作

	public void contextInitialized(ServletContextEvent sce) {
		//PropertiesUtil.init();
		String realPath = sce.getServletContext().getRealPath("/");
		String contextPath = sce.getServletContext().getContextPath();
		String contextName = sce.getServletContext().getServletContextName();
/*
		if (realPath != null) {
			realPath = realPath.replaceAll("\\\\", File.separator);
		} else {
			realPath =File.separator;//不可信
		}
		if (!realPath.endsWith(File.separator)) {
			realPath = realPath + File.separator;
		}

		if (contextPath != null) {
			contextPath = contextPath.replaceAll("\\\\", "/");
		} else {
			contextPath = "/";
		}
		if (!contextPath.endsWith("/")) {
			contextPath = contextPath + "/";
		}

		if (StringUtils.isBlank(contextName)) {
		} else {
			contextName = "/" + contextName;
		}*/

		SysConfig.REALPATH = realPath;// E:/1zzw/Program
		logger.info("realPath:" + realPath);							// Files/apache-tomcat-8.0.15/webapps/MerchantManage/
		
		SysConfig.PATH = contextPath;// /MerchantManage/
		sce.getServletContext().setAttribute("path", contextName);
		logger.info("path:" + contextName);
//		SysConfig.CONTEXTNAME = contextName;// /MerchantManage
		
//		logger.info("CONTEXTPATH:" + contextPath);
		
		
//		sce.getServletContext().setAttribute("CONTEXTPATH", contextPath);
/*
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(this.getClass()
					.getResource("/config.properties").getPath().toString());
			properties.load(inputStream);
			inputStream.close(); // 关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	//	String push_url = properties.getProperty("push_url");
		//SysConfig.PUSH_URL = push_url;

//		sce.getServletContext().setAttribute("PUSH_URL", push_url);

		/*
		 * String urlrewrtie =
		 * sce.getServletContext().getInitParameter("urlrewrite"); boolean
		 * burlrewrtie = false; if (urlrewrtie != null) { burlrewrtie =
		 * Boolean.parseBoolean(urlrewrtie); } Constant.USE_URL_REWRITE =
		 * burlrewrtie; logger.info("Use Urlrewrite:" + burlrewrtie);
		 */
	}

}