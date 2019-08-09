package com.dozenx.web.core.api.client.auth.http.util;

import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.RedisConstants;
import com.dozenx.web.core.api.client.auth.token.service.TokenService;
import com.dozenx.web.util.BeanUtil;
import com.dozenx.web.util.RedisUtil;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午2:57:47
 * 创建作者：周颖
 * 文件名称：TokenClient.java
 * 版本：  v1.0
 * 功能：生成数据中心access_token工具类
 * 修改记录：
 */
public class TokenClient {

    /**生成数据中心access_token服务层*/
    private static TokenService tokenService;
    
    /**
     * 获取tokenService实例
     * @return tokenService
     * @author 周颖  
     * @date 2017年1月18日 下午8:29:56
     */
    public static TokenService getTokenService(){
        if(tokenService == null){
            Object object = BeanUtil.getBean("tokenService");
            tokenService = (TokenService)object;
        }
        return tokenService;
    }
    
    /**
     * 获取数据中心access_token
     * @return access_token
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年1月18日 下午8:30:01
     */
    public static String getAccessToken(){
        String key = RedisConstants.TOKEN_REDIS_KEY;//获取数据中心access_token rediskey
        String accessToken = RedisUtil.get(key);//redis获取access_token
        if(StringUtil.isNotBlank(accessToken)){//如果不为空
            return accessToken;//返回access_token
        }
        return getTokenService().getAccessToken(key);//返回生成的access_token
    }
    
    /**
     * 重置access_token
     * @return access_token
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年1月19日 上午8:56:57
     */
    public static String resetAccessToken(){
        String key = RedisConstants.TOKEN_REDIS_KEY;//获取数据中心access_token rediskey
        return getTokenService().getAccessToken(key);//返回生成的access_token
    }
}