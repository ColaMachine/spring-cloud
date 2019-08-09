package com.dozenx.web.module.pubImage.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.module.pubImage.bean.PubImage;

public interface PubImageMapper {
    
    int deleteByPrimaryKey(Integer id);

    
    int insert(PubImage record);

   
    int insertSelective(PubImage record);

    
    PubImage  selectByPrimaryKey(Integer id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param pubImage
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(PubImage pubImage);

    /**
     * 说明:根据主键修改record完整内容
     * @param pubImage
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(PubImage pubImage);

    /**
     * 说明:根据map查找bean结果集
     * @param pubImage
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PubImage> listByParams(Map pubImage);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param pubImage
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<PubImage> listByParams4Page(Map pubImage);
    
    /**
     * 说明:根据map查找map结果集
     * @param pubImage
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(PubImage pubImage);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param pubImage
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<PubImage> selectBeanByMap4Page(HashMap map);
    
    int countByBean(PubImage record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
