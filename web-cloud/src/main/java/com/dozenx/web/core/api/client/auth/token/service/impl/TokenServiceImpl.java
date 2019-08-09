package com.dozenx.web.core.api.client.auth.token.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.InterfaceException;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.MD5Util;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.api.client.auth.http.util.HttpUtil;
import com.dozenx.web.core.api.client.auth.token.service.TokenService;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午3:00:29
 * 创建作者：周颖
 * 文件名称：TokenServiceImpl.java
 * 版本：  v1.0
 * 功能：获取数据中心access_token实现类
 * 修改记录：
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    /**
     * 获取数据中心access_token
     * @param key rediskey
     * @return access_token oauthToken
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年1月18日 下午8:01:52
     */
    @SuppressWarnings("unchecked")
    public String getAccessToken(String key){
        String url = ConfigUtil.getConfig("token.url");
        String currentTime =""+ System.currentTimeMillis()/1000;
        String token ="";
        String appId = ConfigUtil.getConfig("token.appid");
        String appKey = ConfigUtil.getConfig("token.appkey");
        String tokenUrl = ConfigUtil.getConfig("token.url");

        try {
             token = MD5Util.getStringMD5String(appId+"_"+appKey+"_"+currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = HttpRequestUtil.sendGet(HttpUtil.joinUrlAndParams(url,"token="+token+"&timestamp="+currentTime+"&appid="+appId));//接口返回值
        if(StringUtil.isBlank(result)){//如果为空
            throw new InterfaceException(ErrorMessage.getErrorMsg("err.net.http.result.null.code"),url);//抛接口异常 接口无返回值！
        }
        Map<String, Object> resultMap = JsonUtil.fromJson(result, Map.class);//转成map
        if(resultMap == null){//如果为空
            throw new InterfaceException(ErrorMessage.getErrorMsg("err.param.null"),url);//抛接口异常 接口返回值不允许为空!
        }
        if(!resultMap.get("code").equals("0")){//如果返回失败 fail
            throw new BizException(30505048, ErrorMessage.getErrorMsg("err.net.http.result.null.code"));//抛异常 获取数据中心access_token失败!
        }
        Map<String, Object> data = (Map<String,Object>)resultMap.get("data");//获取data数据
        String oauthToken = (String) data.get("access_token");//access_token
        //Long oauthTimestamp = (Long) data.get("oauthTimestamp");//token生成时间
        //Long loseTimestamp = (Long) data.get("loseTimestamp");//token失效时间
        // int seconds = (int) ((loseTimestamp-oauthTimestamp)/1000);//access_token有效时间
        int seconds = (int) data.get("expires_in");
        RedisUtil.setex(key, oauthToken, seconds);//access_token存到redis
        return oauthToken;//返回access_token
    }


    Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    /** 申请token的url */
//    @Value("${token.url}")
//    private String  tokenUrl; //申请token的url
//    @Value("${token.appid}")
//    private String  appid; //申请token的url
//    @Value("${token.appkey}")
//    private String  appkey; //申请token的url
//    @Override
//    public String getAccessToken(String redisKey) {
//
//        HashMap map =new HashMap();
//        map.put("appid","0001");
//
//        String currentTime =""+ System.currentTimeMillis()/1000;
//        map.put("timestamp",currentTime);
//        try {
//            String token = MD5Util.getStringMD5String(appid+"_"+appkey+"_ss"+currentTime);
//            map.put("token",token);
//            String result = HttpRequestUtil.sendGet(tokenUrl, map);//http://192.168.188.8:3502
//            logger.info("token url return :"+result);
//            JSONObject jsonObject = (JSONObject) JSON.parse(result);
//            JSONObject obj = (JSONObject)jsonObject.get("data");
//            String accessToken =obj.getString("access_token");
//            RedisUtil.setex(redisKey,accessToken , 600*1000);
//            return accessToken;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}