package com.dozenx.web.core.log.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dozenx.web.core.log.bean.OperLog;
import com.dozenx.web.core.log.dao.sql.OperLogSql;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

//@Service("operLogDao")
public interface OperLogDao {
    String Base_Column_List = "id ,module_name ,comp_name ,detail ,user_id ,ip ,user_name , create_time ";

   


    /**
     * 系统日志--分页查询统计
     * @param keywords 关键字配置
     * @param begin 开始记录数
     * @param pageSize 每页记录数
     * @return 系统参数配置集合
     * @author 王作品
     * @date 2017年8月29日 下午3:31:48
     */
    @SelectProvider(type=OperLogSql.class,method="getCountByParam")
    int getCountByParam(@Param("keywords") String keywords, @Param("userName") String userName, @Param("createTime") String createTime,
                        @Param("userId") Integer userId, @Param("begin") Integer begin, @Param("pageSize") Integer pageSize);


    /**
     * 系统日志--分页查询
     * @param keywords 关键字配置
     * @param begin 开始记录数
     * @param pageSize 每页记录数
     * @return 系统参数配置集合
     * @author 王作品
     * @date 2017年8月29日 下午3:31:48
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "moduleName", column = "module_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "compName", column = "comp_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "detail", column = "detail", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "ip", column = "ip", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "create_time", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
    })
    @SelectProvider(type=OperLogSql.class,method="getListByParam")
    List<OperLog> getListByParam(@Param("keywords") String keywords, @Param("userName") String userName, @Param("createTime") String createTime,
                                 @Param("userId") Integer userId, @Param("begin") Integer begin, @Param("pageSize") Integer pageSize);




    /**
     * 插入一条新的数据
     * @param record
     * @return
     * @author 张智威  
     * @date 2017年9月4日 上午10:19:07
     */

    @Insert("insert into np_biz_oper_log ( " + Base_Column_List + ") " + "values (" + "#{id,jdbcType=INTEGER}"
            + ",#{moduleName,jdbcType=VARCHAR}" + ",#{compName,jdbcType=VARCHAR}" + ",#{detail,jdbcType=LONGVARCHAR}"
            + ",#{userId,jdbcType=BIGINT}" + ",#{ip,jdbcType=VARCHAR}" + ",#{userName,jdbcType=VARCHAR}"
            + ",#{createTime,jdbcType=TIMESTAMP} )")

    int insert(OperLog record);




}
