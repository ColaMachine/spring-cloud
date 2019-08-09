/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.log.bean;

import java.sql.Timestamp;

public class SysLog {
    /**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**路径**/
    private String path;
    public String getPath(){
        return path;
    }    public void setPath(String path){
        this.path=path;
    }/**日志类型**/
    private Byte type;
    public Byte getType(){
        return type;
    }    public void setType(Byte type){
        this.type=type;
    }/**代码**/
    private Integer code;
    public Integer getCode(){
        return code;
    }    public void setCode(Integer code){
        this.code=code;
    }/**操作参数**/
    private String param;
    public String getParam(){
        return param;
    }    public void setParam(String param){
        this.param=param;
    }/**用户**/
    private String user;
    public String getUser(){
        return user;
    }    public void setUser(String user){
        this.user=user;
    }/**消息**/
    private String msg;
    public String getMsg(){
        return msg;
    }    public void setMsg(String msg){
        this.msg=msg;
    }/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }/**开始时间**/
    private Timestamp startTime;
    public Timestamp getStartTime(){
        return startTime;
    }    public void setStartTime(Timestamp startTime){
        this.startTime=startTime;
    }
}
