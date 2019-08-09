/*
 * Copyright 2011-2017 CPJIT Group.
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.dozenx.swagger.support.servlet;

import com.dozenx.common.util.StringUtil;
import com.dozenx.swagger.support.Constants;
import com.dozenx.swagger.support.internal.ApiViewWriter;
import com.dozenx.swagger.support.internal.DefaultApiViewWriter;
import com.dozenx.swagger.util.ResourceUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author yonghuan
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class ApiServlet extends HttpServlet implements Constants {
	
	private ApiViewWriter apiViewWriter = new DefaultApiViewWriter();
	private Properties props = new Properties();
	
	@Override
	public void init() throws ServletException {
		InputStream is = ResourceUtil.getResourceAsStream("swagger.properties");
		try {
			props.load(is);
		} catch (IOException ioe) {
			throw new ServletException(ioe);
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if(uri.matches(".*/api/index(/?)(!/)*")) {
			toIndex(req, resp);
		} else if (uri.matches(".*/api/statics/.+")){
			queryStatic(req, resp);
		} else {
			try {
				queryApi(req, resp);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}
	
	private void toIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String lang = request.getParameter("lang");
		if(StringUtil.isBlank(lang)) {
			lang = DEFAULT_LANG;
		}
		
		String suffix = props.getProperty("suffix");
		if(StringUtil.isBlank(suffix)) {
			suffix = "";
		}
		props.put("suffix", suffix);
		apiViewWriter.writeIndex(request, response, lang, props);
	}
	
	/*
	 * @since 1.2.0
	 */
	private void queryStatic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		apiViewWriter.writeStatic(request, response, props);
	}
	
	private void queryApi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Properties props = new Properties();
		InputStream is = ResourceUtil.getResourceAsStream("swagger.properties");
		props.load(is);
		is.close();
		String path = request.getContextPath();
		String host = request.getServerName() + ":" + request.getServerPort() + path;
		props.setProperty("apiHost", host);
		String apiFile = props.getProperty("apiFile");
		if(StringUtil.isBlank(apiFile)) {
			apiFile = DEFAULT_API_FILE;
		}
		String apiFilePath = request.getRealPath(apiFile);
		props.setProperty("apiFile", apiFilePath);
		apiViewWriter.writeApis(request, response, props);
	}
}
