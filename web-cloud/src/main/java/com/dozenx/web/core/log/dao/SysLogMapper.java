package com.dozenx.web.core.log.dao;

import com.dozenx.web.core.log.bean.SysLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface SysLogMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SysLog record);

   
    int insertSelective(SysLog record);

    
    SysLog  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysLog  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysLog record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysLog  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysLog record);

    /**
     * 说明:根据map查找bean结果集
     * @param SysLog  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysLog> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysLog  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysLog> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SysLog  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysLog record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SysLog  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysLog> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysLog record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
