package com.dozenx.web.core.sysConfig.dao;

import com.dozenx.web.core.sysConfig.bean.SysConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysConfigMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SysConfig record);

   
    int insertSelective(SysConfig record);

    
    SysConfig  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param sysConfig
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SysConfig sysConfig);

    /**
     * 说明:根据主键修改record完整内容
     * @param sysConfig
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SysConfig sysConfig);

    /**
     * 说明:根据map查找bean结果集
     * @param sysConfig
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysConfig> listByParams(Map sysConfig);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param sysConfig
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SysConfig> listByParams4Page(Map sysConfig);
    
    /**
     * 说明:根据map查找map结果集
     * @param sysConfig
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SysConfig sysConfig);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param sysConfig
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SysConfig> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SysConfig record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
