package com.dozenx.web.core.page;

public class Page {
	private int curPage;
	private int totalPage;
	private int pageSize;
	private int totalCount;
	private int beginIndex;
	private boolean hasPrePage;
	private boolean hasNextPage;
	public Page(int everyPage, int totalCount2, int totalPage, int currentPage,
			int beginIndex, boolean hasPrePage, boolean hasNextPage) {
		this.pageSize=everyPage;
		this.totalCount=totalCount2;
		this.totalPage=totalPage;
		this.curPage=currentPage;
		this.beginIndex=beginIndex;
		this.hasNextPage=hasNextPage;
		this.hasPrePage=hasPrePage;
	}
	public Page(int curPage, int pageSize) {
		this.pageSize=pageSize;
		this.curPage=curPage;
	}
	public Page() {
		// TODO Auto-generated constructor stub
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public boolean isHasPrePage() {
		return hasPrePage;
	}
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void init(int curPage2, int pageSize2, int totalCount2) {
		this.curPage=curPage2;
		this.pageSize=pageSize2;
		this.totalCount=totalCount2;
	}

}
