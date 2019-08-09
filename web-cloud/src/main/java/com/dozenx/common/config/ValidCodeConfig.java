package com.dozenx.common.config;

import com.dozenx.common.config.SystemValidCodeConfig;

import java.util.HashMap;

/**
 * @author dozen.zhang
 *
 */
public class ValidCodeConfig {
    private String serverUrl;
    public String getServerUrl() {
        return serverUrl;
    }
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    private int smsLength;
    private int imgFailTimes;

    public int getImgFailTimes() {
        return imgFailTimes;
    }

    public void setImgFailTimes(int imgFailTimes) {
        this.imgFailTimes = imgFailTimes;
    }

    public int getSmsFailTimes() {
        return smsFailTimes;
    }

    public void setSmsFailTimes(int smsFailTimes) {
        this.smsFailTimes = smsFailTimes;
    }

    private int smsFailTimes;
    private int imgLength;
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
    public HashMap<String, SystemValidCodeConfig> getSystems() {
        return systems;
    }
    public void setSystems(HashMap<String, SystemValidCodeConfig> systems) {
        this.systems = systems;
    }
    private int smsCharType;
    private int imgCharType;
    private int smsLiveTime;
    private int imgLiveTime;
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
