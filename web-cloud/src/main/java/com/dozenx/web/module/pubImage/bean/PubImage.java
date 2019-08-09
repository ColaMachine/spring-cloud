/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.pubImage.bean;
import java.sql.Timestamp;
import java.util.Date;

public class PubImage {
    
/**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }
/**文件的绝对路径**/
    private String path;
    public String getPath(){
        return path;
    }    public void setPath(String path){
        this.path=path;
    }
/**原始名称**/
    private String oriName;
    public String getOriName(){
        return oriName;
    }    public void setOriName(String oriName){
        this.oriName=oriName;
    }
/**文件名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }
/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }
/**绝对路径**/
    private String absPath;
    public String getAbsPath(){
        return absPath;
    }    public void setAbsPath(String absPath){
        this.absPath=absPath;
    }
/**相对路径**/
    private String relPath;
    public String getRelPath(){
        return relPath;
    }    public void setRelPath(String relPath){
        this.relPath=relPath;
    }
/**指纹**/
    private String figure;
    public String getFigure(){
        return figure;
    }    public void setFigure(String figure){
        this.figure=figure;
    }
/**照片上传时的Ip**/
    private String uploadIp;
    public String getUploadIp(){
        return uploadIp;
    }    public void setUploadIp(String uploadIp){
        this.uploadIp=uploadIp;
    }
/**上传照片人的Id**/
    private String creator;
    public String getCreator(){
        return creator;
    }    public void setCreator(String creator){
        this.creator=creator;
    }
/**上传人的姓名**/
    private String creatorName;
    public String getCreatorName(){
        return creatorName;
    }    public void setCreatorName(String creatorName){
        this.creatorName=creatorName;
    }
/**上传照片的时间**/
    private Date createDate;
    public Date getCreateDate(){
        return createDate;
    }    public void setCreateDate(Date createDate){
        this.createDate=createDate;
    }
/**照片的创建时间**/
    private Date lastModify;
    public Date getLastModify(){
        return lastModify;
    }    public void setLastModify(Date lastModify){
        this.lastModify=lastModify;
    }
/**照片的状态 0 使用状态 1 移除状态 9 彻底删除状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
/**顺序id**/
    private Integer order;
    public Integer getOrder(){
        return order;
    }    public void setOrder(Integer order){
        this.order=order;
    }
/**父组件id**/
    private Integer pid;
    public Integer getPid(){
        return pid;
    }    public void setPid(Integer pid){
        this.pid=pid;
    }
}
