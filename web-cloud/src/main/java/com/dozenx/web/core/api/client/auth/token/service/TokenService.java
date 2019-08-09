package com.dozenx.web.core.api.client.auth.token.service;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午3:00:08
 * 创建作者：周颖
 * 文件名称：TokenService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface TokenService {

    /**
     * 获取数据中心access_token
     * @param key redis key
     * @return access_token
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年1月19日 上午8:36:57
     */
    String getAccessToken(String key);
}
