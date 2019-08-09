package com.dozenx.web.core.log;

import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.core.page.Page;

public class ResultDTO {
	private Integer r;
	private Object data;
	private String msg;
	private Page page;

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

	private Object other;
	
	public ResultDTO(Integer r, Object data, String msg){
		this.r = r;
		this.data = data;
		this.msg = msg;
	}
	
	public ResultDTO(Integer r, Object data, String msg, Page page){
		this.r = r;
		this.data = data;
		this.msg = msg;
		this.page = page;
	}
	
	public ResultDTO() {
		// TODO Auto-generated constructor stub
	}

	public ResultDTO(int i, String msg) {
		this.r=i;
		this.msg=msg;
	}

	

	

	public Integer getR() {
		return r;
	}
	public void setR(Integer r) {
		this.r = r;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public void clone(ResultDTO result){
		this.r=result.r;
		this.data=result.data;
		this.msg=result.msg;
		this.page=result.page;
	}
	
	/**
	 * 说明:判断是否正确
	 * @return
	 * @return boolean
	 * @author dozen.zhang
	 * @date 2015年12月14日上午11:44:59
	 */
	public boolean isRight(){
		return this.r== ResultUtil.succ;
	}
}

