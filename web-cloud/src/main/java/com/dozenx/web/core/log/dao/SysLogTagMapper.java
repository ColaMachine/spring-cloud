package com.dozenx.web.core.log.dao;

import com.dozenx.web.core.log.bean.SysLogTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface SysLogTagMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SysLogTag record);

   
    int insertSelective(SysLogTag record);

    
    SysLogTag  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SysLogTag  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysLogTag record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SysLogTag  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysLogTag record);

    /**
     * 说明:根据map查找bean结果集
     * @param SysLogTag  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysLogTag> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SysLogTag  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysLogTag> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SysLogTag  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysLogTag record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SysLogTag  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysLogTag> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysLogTag record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
