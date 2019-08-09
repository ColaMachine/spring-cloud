package com.dozenx.web.core.log.service;


import com.dozenx.web.core.log.bean.OperLog;
import com.dozenx.web.core.page.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OperLogService {


    //void getListByParam(Map<String, Object> maps);

    /**
     * 说明:新增操作日志
     *
     * @param operLog 操作日志
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    void save(OperLog operLog);

    /**
     * * 分页查询接口
     *
     * @param userName 用户名
     * @param keywords 关键字查询条件
     * @param page     分页
     * @author 王作品
     * @date 2017年9月29日 下午2:24:22
     */

    List<OperLog> getListByParam(String keywords, String userName, String date, String createTimeBegin, String createTimeEnd, Integer userId, Page page);


    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<OperLog> listByParams4Page(HashMap params);//

}