package com.dozenx.web.core.page;

import java.util.List;


public class ListPage<T> {
	private Page page;

	private List<T> list;
	
	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<T> getList() {
		return this.list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
