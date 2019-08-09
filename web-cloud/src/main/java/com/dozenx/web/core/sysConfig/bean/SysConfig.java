/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.sysConfig.bean;

public class SysConfig {
    /**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**名称**/
    private String key;
    public String getKey(){
        return key;
    }    public void setKey(String key){
        this.key=key;
    }/**对应值**/
    private String value;
    public String getValue(){
        return value;
    }    public void setValue(String value){
        this.value=value;
    }/**说明**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }
}
