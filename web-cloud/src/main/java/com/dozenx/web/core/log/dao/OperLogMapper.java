package com.dozenx.web.core.log.dao;
import com.dozenx.web.core.log.bean.OperLog;

import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;

public interface OperLogMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(OperLog record);

   
    int insertSelective(OperLog record);

    
    OperLog  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param operLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(OperLog operLog);

    /**
     * 说明:根据主键修改record完整内容
     * @param operLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(OperLog operLog);

    /**
     * 说明:根据map查找bean结果集
     * @param operLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<OperLog> listByParams(Map operLog);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param operLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<OperLog> listByParams4Page(Map operLog);
    
    /**
     * 说明:根据map查找map结果集
     * @param operLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(OperLog operLog);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param operLog
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<OperLog> selectBeanByMap4Page(HashMap map);
    
    int countByBean(OperLog record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
