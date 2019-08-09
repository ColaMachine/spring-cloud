/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月9日
 * 文件说明: 
 */
package com.dozenx.web.core.log.service;

import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.log.bean.AppExceptionLog;
import com.dozenx.web.core.log.bean.AppExceptionLogExample;
import com.dozenx.web.core.log.bean.FwLog;
import com.dozenx.web.core.log.bean.FwLogExample;
import com.dozenx.web.core.log.dao.AppExceptionLogMapper;
import com.dozenx.web.core.log.dao.FwLogMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
@Transactional
@Service("logService")
public class LogService {
	@Resource(name = "appExceptionLogMapper")
	private AppExceptionLogMapper appExceptionLogMapper;
	/*public void setAppExceptionLogMapper(AppExceptionLogMapper appExceptionLogMapper) {
		this.appExceptionLogMapper = appExceptionLogMapper;
	}*/
	
	@Resource(name = "fwLogMapper")
	private FwLogMapper fwLogMapper;
	/*public void setFwLogMapper(FwLogMapper fwLogMapper) {
		this.fwLogMapper = fwLogMapper;
	}*/

	
	/**
	 * 说明:记录访问记录
	 * @param log
	 * @return void
	 * @author dozen.zhang
	 * @date 2015年11月11日上午10:11:02
	 */
	public void recordRequest(FwLog log){
		fwLogMapper.insert(log);
	}

	/**
	 * 说明:	访问记录分页列表
	 * @param example
	 * @return
	 * @return List<FwLog>
	 * @author dozen.zhang
	 * @date 2015年11月11日上午9:50:50
	 */
	public List<FwLog> listRequests(FwLogExample example){
		 return fwLogMapper.selectByExample4Page(example);
	}

	/**
	 * 说明:	记录异常日志
	 * @param request
	 * @param e
	 * @return void
	 * @author dozen.zhang
	 * @date 2015年11月11日上午10:11:30
	 */
	public void recordException(HttpServletRequest request,Exception e){

		//StackTraceElement[]  s = Thread.currentThread().getStackTrace();
		//获取service
		AppExceptionLog log =new AppExceptionLog();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		e.printStackTrace(new PrintStream(baos));  
		String exception = baos.toString();  
		log.setMsg(exception);
//		log.setClassName(e.getCause().toString());
		log.setClassName(e.getStackTrace()[0].getClassName());
		log.setTime(new Date());
		log.setLineNumber(e.getStackTrace()[0].getLineNumber());
		Gson gson=new Gson();
		String sid = request.getParameter("sid");
		if(StringUtil.isBlank(sid))
			sid="0";
		log.setSid(Integer.valueOf(sid));
		log.setParams(gson.toJson(request.getParameterMap()));
		log.setRequesturl(request.getRequestURI());
//		this.appExceptionLogService.save(log);
		
		this.appExceptionLogMapper.insert(log);
		//获取异常抛出的controller
		//获取异常抛出的service
		//获取request 参数
		//关联requesturl
		//保存异常
		//保存简短异常
	
	}
	

	/**
	 * 说明:异常记录分页
	 * @param example
	 * @return
	 * @return Object
	 * @author dozen.zhang
	 * @date 2015年11月11日上午10:02:21
	 */
	public List<AppExceptionLog> listExceptionLog(AppExceptionLogExample example) {
		return appExceptionLogMapper.selectByExample(example);
	}
}
