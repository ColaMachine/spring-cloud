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
package com.dozenx.swagger.support.internal;

import com.alibaba.fastjson.JSONWriter;
import com.dozenx.common.util.StringUtil;
import com.dozenx.swagger.APIParseable;
import com.dozenx.swagger.APIParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author yonghuan
 * @since 1.2.0
 */
public class DefaultApiViewWriter implements ApiViewWriter {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultApiViewWriter.class);
	private static boolean scanfed = false;
	
	protected String getTemplateName() {
		return "api.ftlh";
	}
	
	
	@Override
	public void writeIndex(HttpServletRequest request, HttpServletResponse response, String lang, Properties props)
			throws IOException {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("lang", lang);
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		root.put("basePath", basePath);
		request.setAttribute("basePath", basePath);
		request.setAttribute("lang", lang);
		String host = request.getServerName() + ":" + request.getServerPort() + path;
		String suffix = props.getProperty("suffix");

		if(StringUtil.isBlank(suffix)) {
			suffix = "";
		}
		root.put("getApisUrl","http://" + host + "/api" + suffix);
		request.setAttribute("getApisUrl","http://" + host + "/api" + suffix);
		root.put("apiDescription", props.getProperty("apiDescription"));
		request.setAttribute("apiDescription", props.getProperty("apiDescription"));
		root.put("apiTitle", props.getProperty("apiTitle"));
		request.setAttribute("apiTitle", props.getProperty("apiTitle"));
		root.put("apiVersion", props.getProperty("apiVersion"));
		request.setAttribute("apiVersion", props.getProperty("apiVersion"));
		root.put("suffix", suffix);
		request.setAttribute("suffix", suffix);
//        Template template = FreemarkerUtils.getTemplate(getTemplateName());
        response.setContentType("text/html;charset=utf-8");
        OutputStream out = response.getOutputStream();

		byte[] readContent = new byte[254];
        try {
			InputStream fileInputStream = this.getClass().getResourceAsStream("/com/cpj/swagger/support/internal/templates/ftlh/api.ftlh");
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream));
			int length =0;
			String s = fileReader.readLine();
			StringBuffer stringBuffer =new StringBuffer(1024);
			while(s!=null){
				stringBuffer.append(s).append("\r\n");
				s = fileReader.readLine();
			}
			String content=stringBuffer.toString();

			content=content.replaceAll("\\$\\{basePath\\}",basePath);
			content =content.replaceAll("\\$\\{suffix\\}",suffix);
			content =content.replaceAll("\\$\\{getApisUrl\\}","http://" + host + "/api" + suffix);
			content =content.replaceAll("\\$\\{getApisUrl\\}","http://" + host + "/api" + suffix);
			content =content.replaceAll("\\$\\{apiVersion\\}",props.getProperty("apiVersion"));
			content =content.replaceAll("\\$\\{apiTitle\\}",props.getProperty("apiTitle"));
			content =content.replaceAll("\\$\\{apiDescription\\}",props.getProperty("apiDescription"));
			content =content.replaceAll("\\$\\{lang\\}",lang);

			fileInputStream.close();
			out.write(content.getBytes());
		} catch (IOException e) {
			throw new IOException(e);
		}

        out.flush();
        out.close();
	}

	@Override
	public void writeApis(HttpServletRequest request, HttpServletResponse response, Properties props)
			throws Exception {
		APIParseable restParser = APIParser.newInstance(props);
		response.setContentType("application/json;charset=utf-8");
		String devMode = props.getProperty("devMode");
		if(Boolean.valueOf(devMode)) {
			Object apis = restParser.parseAndNotStore();
			JSONWriter writer = new JSONWriter(response.getWriter());
			writer.writeObject(apis);
			writer.flush();
			writer.close();
		} else {
			if(!scanfed) {
				restParser.parse();
				scanfed = true;
			}
			byte[] bs = Files.readAllBytes(Paths.get(props.getProperty("apiFile")));
			OutputStream out = response.getOutputStream();
			out.write(bs);
			out.flush();
			out.close();
		}
	}

	/**
	 * @since 1.2.2
	 */
	protected String buildResourcePath(HttpServletRequest request, Properties config) {
		String uri = request.getRequestURI();
		String suffix = (String) config.get("suffix");
		if(suffix != null) {
			int index = uri.lastIndexOf(suffix);
			if(index > 0) {
				 uri = uri.substring(0, index);
			}
		}
		String path = uri.substring(uri.indexOf("statics")+7);
		path = "com/cpj/swagger/support/internal/statics"+path;
		return path;
	}
	
	@Deprecated
	protected String buildResourcePath(HttpServletRequest request) {
		return buildResourcePath(request, null);
	}
	
	@Override
	public void writeStatic(HttpServletRequest request, HttpServletResponse response, Properties props) throws IOException {
		String path = buildResourcePath(request, props);
		LOG.debug("获取web资源文件： " + path);
		String contentType = FileTypeMap.getContentType(path);
		response.setContentType(contentType);
		InputStream resource = DefaultApiViewWriter.class.getClassLoader().getResourceAsStream(path);
		if(resource == null) {
			response.sendError(404);
			return;
		}
		
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			byte[] buff = new byte[512];
			int len = -1;
			while((len=resource.read(buff))!=-1) {
				out.write(buff, 0, len);
			}
			out.flush();
		} finally {
			try {
				if(resource != null) {
					resource.close();
				}
			}catch(IOException e) {
			}
			try {
				if(out != null) {
					out.close();
				}
			}catch(IOException e) {
			}
		}
	}
	
	@Deprecated
	@Override
	public void writeStatic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeStatic(request, response, null);
	}
}
