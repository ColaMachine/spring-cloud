package com.dozenx.common.config;

import java.util.HashMap;

/**
 * @author dozen.zhang
 *
 */
public class SystemValidCodeConfig {
    private String systemno;
    private int smsCharType;
    private int imgCharType;
    private int smsLength;
    private int imgLength;
    private int smsLiveTime;
    private int imgLiveTime;

    public int getMaxRequestTime() {
        return maxRequestTime;
    }

    public void setMaxRequestTime(int maxRequestTime) {
        this.maxRequestTime = maxRequestTime;
    }

    public Long getRangeTime() {
        return rangeTime;
    }

    public void setRangeTime(Long rangeTime) {
        this.rangeTime = rangeTime;
    }

    private int maxRequestTime;
    private Long rangeTime;
    
    private int smsErrTime;
    private int imgErrTime;
    public String getSystemno() {
        return systemno;
    }
    public void setSystemno(String systemno) {
        this.systemno = systemno;
    }
    public int getSmsCharType() {
        return smsCharType;
    }
    public void setSmsCharType(int smsCharType) {
        this.smsCharType = smsCharType;
    }
    public int getImgCharType() {
        return imgCharType;
    }
    public void setImgCharType(int imgCharType) {
        this.imgCharType = imgCharType;
    }
   
    public int getSmsLength() {
        return smsLength;
    }
    public void setSmsLength(int smsLength) {
        this.smsLength = smsLength;
    }
    public int getImgLength() {
        return imgLength;
    }
    public void setImgLength(int imgLength) {
        this.imgLength = imgLength;
    }
    public HashMap<String, SystemValidCodeConfig> getSystems() {
        return systems;
    }
    public void setSystems(HashMap<String, SystemValidCodeConfig> systems) {
        this.systems = systems;
    }
    public int getSmsLiveTime() {
        return smsLiveTime;
    }
    public void setSmsLiveTime(int smsLiveTime) {
        this.smsLiveTime = smsLiveTime;
    }
    public int getImgLiveTime() {
        return imgLiveTime;
    }
    public void setImgLiveTime(int imgLiveTime) {
        this.imgLiveTime = imgLiveTime;
    }
    public int getSmsErrTime() {
        return smsErrTime;
    }
    public void setSmsErrTime(int smsErrTime) {
        this.smsErrTime = smsErrTime;
    }
    public int getImgErrTime() {
        return imgErrTime;
    }
    public void setImgErrTime(int imgErrTime) {
        this.imgErrTime = imgErrTime;
    }
    private int smsRefreshTime;
    private int imgRefreshTime;
    private HashMap<String,SystemValidCodeConfig> systems;
    public int getSmsRefreshTime() {
        return smsRefreshTime;
    }
    public void setSmsRefreshTime(int smsRefreshTime) {
        this.smsRefreshTime = smsRefreshTime;
    }
    public int getImgRefreshTime() {
        return imgRefreshTime;
    }
    public void setImgRefreshTime(int imgRefreshTime) {
        this.imgRefreshTime = imgRefreshTime;
    }
    
}
