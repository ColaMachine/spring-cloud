package com.dozenx.web.core.log.dao.sql;

import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年8月29日 下午5:17:46
 * 创建作者：王作品
 * 文件名称：OperLogSql.java
 * 版本：  v1.0
 * 功能：日志sql
 * 修改记录：
 */
public class OperLogSql {
    private static Logger logger = LoggerFactory.getLogger(OperLogSql.class);

    public String getListByParam(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select id ,user_name,module_name ,comp_name ,detail ,ip,")
                .append(" create_time ")
                .append("from np_biz_oper_log ")
                .append("where 1=1 ");
        String createTime=(String) params.get("createTime");
        String keywords = (String) params.get("keywords");
        String userName = (String) params.get("userName");
        Integer userId =(Integer)params.get("userId");
        sql.append("and create_time between '"+createTime+"' and '"+createTime+"' + INTERVAL '1' day ");
        if(userId !=null){
           sql.append(" and user_id= #{userId} ");
        }
        if(StringUtil.isNotBlank(userName)){
            sql.append("and user_name like concat('%',#{userName},'%') ");
        }
        if(StringUtil.isNotBlank(keywords)){
            sql.append("and concat(ifnull(module_name,''),ifnull(comp_name,''),ifnull(detail,''))  like concat('%',#{keywords},'%') ");
        }
        sql.append(" order by create_time desc ");//排序倒序
        sql.append(" limit #{begin},#{pageSize}");
        logger.debug("系统操作日志：获取sql-----:: " +sql.toString());
        return sql.toString();
    }


    public String getCountByParam(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(1) as totalRecord ")
                .append("from np_biz_oper_log ")
                .append("where 1=1 ");
        String createTime=(String) params.get("createTime");
        String keywords = (String) params.get("keywords");
        String userName = (String) params.get("userName");
        Integer userId=(Integer) params.get("userId");
        sql.append("and create_time between '"+createTime+"' and '"+createTime+"' + INTERVAL '1' day ");
        if(userId !=null){
            sql.append(" and user_id= #{userId} ");
        }
        if(StringUtil.isNotBlank(userName)){
            sql.append("and user_name like concat('%',#{userName},'%') ");
        }
        if(StringUtil.isNotBlank(keywords)){
            sql.append("and concat(ifnull(module_name,''),ifnull(comp_name,''),ifnull(detail,''))  like concat('%',#{keywords},'%') ");
        }
        logger.debug("系统操作日志：计算总数sql： " +sql.toString());
        return sql.toString();
    }
}
