package com.dozenx.web.module.email.email.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.email.email.bean.Email;

public interface EmailMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(Email record);

   
    int insertSelective(Email record);

    Email  selectByPrimaryKey(Long id);
    /**
     * 说明:根据主键修改所存在属性内容
     * @param email
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Email email);

    /**
     * 说明:根据主键修改record完整内容
     * @param email
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Email email);

    /**
     * 说明:根据map查找bean结果集
     * @param email
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Email> listByParams(Map email);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param email
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Email> listByParams4Page(Map email);
    
    /**
     * 说明:根据map查找map结果集
     * @param email
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Email email);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param email
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Email> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Email record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      


     void insertBatch(List<Email> list);
}
