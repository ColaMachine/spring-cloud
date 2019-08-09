package com.dozenx.web.util;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午7:58:28
 * 创建作者：周颖
 * 文件名称：SqlUtil.java
 * 版本：  v1.0
 * 功能：sql工具类
 * 修改记录：
 */
public class SqlUtil {

    /**
     * in 查询 sql拼接
     * @param columnName 数据库对应的字段名称
     * @param paramName 参数名
     * @param columnValueArray 参数值数据
     * @param sql sql
     * @author 许小满
     * @date 2016年9月30日 下午5:28:19
     */
    public static void in(String columnName, String paramName, Long[] columnValueArray, StringBuffer sql){
        int maxLength = columnValueArray != null ? columnValueArray.length : 0;
        if(maxLength <= 0){
            return;
        }
        sql.append("and ").append(columnName).append(" in(");
        for(int i=0; i<maxLength; i++){
            sql.append("#{").append(paramName).append("[").append(i).append("]}");
            if(i < (maxLength - 1)){
                sql.append(",");
            }
        }
        sql.append(") ");
    }

    /**
     * in 查询 sql拼接
     * @param columnName 数据库对应的字段名称
     * @param paramName 参数名
     * @param columnValueArray 参数值数据
     * @param sql sql
     * @author 许小满
     * @date 2016年9月30日 下午5:28:19
     */
    public static void inStringArr(String columnName, String paramName, String[] columnValueArray, StringBuffer sql){
        int maxLength = columnValueArray != null ? columnValueArray.length : 0;
        if(maxLength <= 0){
            return;
        }
        sql.append("and ").append(columnName).append(" in(");
        for(int i=0; i<maxLength; i++){
            sql.append("#{").append(paramName).append("[").append(i).append("]}");
            if(i < (maxLength - 1)){
                sql.append(",");
            }
        }
        sql.append(") ");
    }

    /**
     * not in查询sql拼接
     * @param columnName 数据库对应的字段名称
     * @param paramName 参数名
     * @param columnValueArray 参数值数据
     * @param sql sql
     * @author 周颖
     * @date 2017年8月2日 下午2:19:56
     */
    public static void notIn(String columnName, String paramName, Long[] columnValueArray, StringBuffer sql){
        int maxLength = columnValueArray != null ? columnValueArray.length : 0;
        if(maxLength <= 0){
            return;
        }
        sql.append("and ").append(columnName).append(" not in(");
        for(int i=0; i<maxLength; i++){
            sql.append("#{").append(paramName).append("[").append(i).append("]}");
            if(i < (maxLength - 1)){
                sql.append(",");
            }
        }
        sql.append(") ");
    }
}