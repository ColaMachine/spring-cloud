/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.sms.bean;
import java.sql.Timestamp;

public class SmsRecord {
    /**id**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**手机号码**/
    private String phone;
    public String getPhone(){
        return phone;
    }    public void setPhone(String phone){
        this.phone=phone;
    }/**系统名称**/
    private String systemNo;
    public String getSystemNo(){
        return systemNo;
    }    public void setSystemNo(String systemNo){
        this.systemNo=systemNo;
    }/**发送时间**/
    private Timestamp sendTime;
    public Timestamp getSendTime(){
        return sendTime;
    }    public void setSendTime(Timestamp sendTime){
        this.sendTime=sendTime;
    }/**内容**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }/**发送状态**/
    private Byte status;
    public Byte getStatus(){
        return status;
    }    public void setStatus(Byte status){
        this.status=status;
    }/**失败原因**/
    private String reason;
    public String getReason(){
        return reason;
    }    public void setReason(String reason){
        this.reason=reason;
    }

    private Long createUser;

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
