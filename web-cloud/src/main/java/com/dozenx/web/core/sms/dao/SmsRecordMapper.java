package com.dozenx.web.core.sms.dao;

import com.dozenx.web.core.sms.bean.SmsRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SmsRecordMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(SmsRecord record);

   
    int insertSelective(SmsRecord record);

    
    SmsRecord  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param SmsRecord  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(SmsRecord record);

    /**
     * 说明:根据主键修改record完整内容
     * @param SmsRecord  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(SmsRecord record);

    /**
     * 说明:根据map查找bean结果集
     * @param SmsRecord  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsRecord> listByParams(Map map);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param SmsRecord  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<SmsRecord> listByParams4Page(Map map);
    
    /**
     * 说明:根据map查找map结果集
     * @param SmsRecord  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(SmsRecord record);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param SmsRecord  
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<SmsRecord> selectBeanByMap4Page(HashMap map);
    
    int countByBean(SmsRecord record);*/
    
    int countByParams(HashMap map);
      
      
}
