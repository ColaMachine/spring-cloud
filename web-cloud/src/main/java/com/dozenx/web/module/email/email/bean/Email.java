/**
 * 版权所有： dozen.zhang
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2019年02月21日
 * 文件说明: 
 */
package com.dozenx.web.module.email.email.bean;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
/**
 *邮件服务实体类
 **/
public class Email {
        /**编号 null**/
    private Long id;
    /**收件人 null**/
    private String to;
    /**标题 null**/
    private String title;
    /**内容 null**/
    private String content;
    /**状态 null**/
    private Integer status;
    /**模块 null**/
    private Integer module;
    /**创建时间 null**/
    private Timestamp createTime;
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getTo(){
        return to;
    }
    public void setTo(String to){
        this.to=to;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status=status;
    }
    public Integer getModule(){
        return module;
    }
    public void setModule(Integer module){
        this.module=module;
    }
    public Timestamp getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }

}
