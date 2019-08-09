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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @author yonghuan
 * @since 1.2.0
 */
public interface ApiViewWriter {

	void writeIndex(HttpServletRequest request, HttpServletResponse response, String lang, Properties props) throws IOException;
	
	void writeApis(HttpServletRequest request, HttpServletResponse response, Properties props) throws Exception;
	
	@Deprecated
	void writeStatic(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	/**
	 * @since 1.2.2
	 */
	void writeStatic(HttpServletRequest request, HttpServletResponse response, Properties props) throws IOException;
}
