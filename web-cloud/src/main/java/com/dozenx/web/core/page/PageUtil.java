package com.dozenx.web.core.page;


import com.dozenx.common.util.MapUtils;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {
	public static Page createPage(int everyPage, int currentPage, int totalCount) {
        everyPage = getEveryPage(everyPage);
        currentPage = getCurrentPage(currentPage);
        int totalPage = getTotalPage(everyPage, totalCount);
        if(currentPage>totalPage){
        	currentPage=totalPage;
        }
        int beginIndex = getBeginIndex(everyPage, currentPage);

        boolean hasPrePage = getHasPrePage(currentPage);
        boolean hasNextPage = getHasNextPage(totalPage, currentPage);
        return new Page(everyPage, totalCount, totalPage, currentPage,
                beginIndex, hasPrePage,  hasNextPage);
    }

    public static void init(Page page , int everyPage, int currentPage, int totalCount) {
        everyPage = getEveryPage(everyPage);
        currentPage = getCurrentPage(currentPage);
        int totalPage = getTotalPage(everyPage, totalCount);
        if(currentPage>totalPage){
            currentPage=totalPage;
        }
        int beginIndex = getBeginIndex(everyPage, currentPage);

        boolean hasPrePage = getHasPrePage(currentPage);
        boolean hasNextPage = getHasNextPage(totalPage, currentPage);
        page.setPageSize(everyPage);
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);
        page.setCurPage(currentPage);
        page.setBeginIndex(beginIndex);
        page.setHasPrePage(hasPrePage);
        page.setHasNextPage(hasNextPage);

    }

    public static Page createPage(Page page, int totalCount) {
        int everyPage = getEveryPage(page.getPageSize());
        int currentPage = getCurrentPage(page.getCurPage());
        int totalPage = getTotalPage(everyPage, totalCount);
        int beginIndex = getBeginIndex(everyPage, currentPage);
        boolean hasPrePage = getHasPrePage(currentPage);
        boolean hasNextPage = getHasNextPage(totalPage, currentPage);
        return new Page(everyPage, totalCount, totalPage, currentPage,
                beginIndex, hasPrePage,  hasNextPage);  
    }  
      
    //设置每页显示记录数  
    public static int getEveryPage(int everyPage) {  
        return everyPage == 0 ? 10 : everyPage;  
    }  
      
    //设置当前页  
    public static int getCurrentPage(int currentPage) {  
        return currentPage == 0 ? 1 : currentPage;  
    }  
      
    //设置总页数,需要总记录数，每页显示多少  
    public static int getTotalPage(int everyPage,int totalCount) {  
        int totalPage = 0;  
        if(totalCount % everyPage == 0) {  
            totalPage = totalCount / everyPage;  
        } else {  
            totalPage = totalCount / everyPage + 1;  
        }  
        return totalPage;  
    }  
      
    //设置起始点，需要每页显示多少，当前页  
    public static int getBeginIndex(int everyPage,int currentPage) {  
    	if(currentPage==0){
    		return 0;
    	}
        return (currentPage - 1) * everyPage;  
    }  
      
    //设置是否有上一页，需要当前页  
    public static boolean getHasPrePage(int currentPage) {  
        return currentPage == 1 ? false : true;  
    }  
      
    //设置是否有下一个，需要总页数和当前页  
    public static boolean getHasNextPage(int totalPage, int currentPage) {  
        return currentPage == totalPage || totalPage == 0 ? false : true;  
    }


    public static Page getPage(Map params){

		/*if(StringUtil.isBlank(curPage)||StringUtil.isBlank(pageSize) ){
			return null;
		}*/
        int curPage = MapUtils.getInteger(params,"curPage",1);
        int pageSize = MapUtils.getInteger(params,"pageSize",20);

        Page page =new Page();
        page.setCurPage(curPage);
        page.setPageSize(pageSize);
        return page;
    }

    /**
     * 分页工具 初始化分页参数
     * @param params
     * @param totalCount
     */
    public static void startPage(HashMap params, int totalCount){
        Page page = (Page)params.get("page");
        init(page,page.getPageSize(),page.getCurPage(),totalCount);
        params.put("begin",page.getBeginIndex());
        params.put("pageSize",page.getPageSize());
    }
}
