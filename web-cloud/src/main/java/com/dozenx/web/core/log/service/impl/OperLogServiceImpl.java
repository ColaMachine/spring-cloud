/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.core.log.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dozenx.web.core.log.bean.OperLog;
import com.dozenx.web.core.log.dao.OperLogDao;
import com.dozenx.web.core.log.dao.OperLogMapper;
import com.dozenx.web.core.log.service.OperLogService;
import com.dozenx.web.core.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("operLogService")
public class OperLogServiceImpl implements OperLogService {


    private static final Logger logger = LoggerFactory.getLogger(OperLogServiceImpl.class);
    /**操作日志dao**/
    @Resource
    private OperLogMapper operLogMapper;
    /**
     * 操作日志分页查询
     */
    @Override
    public List<OperLog> getListByParam(String keywords, String userName, String date,String createTimeBegin,String createTimeEnd,Integer userId, Page page) {
        logger.debug("系统操作日志：service传入----:: " + "关键字----" + keywords + "用户名----" + userName + "开始时间----" + createTimeBegin
                + "page---" + page);
        HashMap params =new HashMap();
        params.put("keywords",keywords);
        params.put("userName",userName);
        params.put("page",page);
        params.put("date",date);
        params.put("createTimeBegin",createTimeBegin);
        params.put("createTimeEnd",createTimeEnd);
        List<OperLog> operLogList = operLogMapper.listByParams4Page(params);
        return operLogList;
    }




    /**
     * @param operLog 操作日志新增
     * @author 张智威  
     * @date 2017年9月4日 上午10:34:42
     */
    public void save(OperLog operLog) {

        operLog.setCreateTime(new Timestamp(new Date().getTime()));

        operLogMapper.insert(operLog);

    }




    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<OperLog> listByParams4Page(HashMap params) {
        return operLogMapper.listByParams4Page(params);
    }
}
