package com.dozenx.web.util;

import com.dozenx.common.util.CastUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * baidu tecent 经纬度api util
 * @author dozen.zhang
 */
public class GeoCoderUtil {

    private static Logger logger = LoggerFactory.getLogger(GeoCoderUtil.class);


    public static void main(String[] args) {
        String result = GeoCoderUtil.bdtoTencentGeoCoder("海南省三亚南边海路170号盐场码头");
        logger.debug("result=" + result);
    }

    /**
     * 百度坐标转腾讯坐标
     * @param address
     * @author dozen.zhang
     * @return
     */

    public static String bdtoTencentGeoCoder(String address){
        String url="http://api.map.baidu.com/geocoder/v2/";
        String params="address="+address+"&output=json&ak="+
                ConfigUtil.getConfig("BAIDU_AK")+"&callback=showLocation&ret_coordtype=gcj02ll";
        String result= HttpRequestUtil.sendGet(url,params);  //百度获取结果
        logger.debug("baidu return:" + result);
        int index = result.indexOf("(");
        result=result.substring(index+1);
        result=result.substring(0,result.length()-1);
        Map<String ,Object> re= JsonUtil.fromJson(result,Map.class);
        return  judge(re);
    }



    /**
     * 腾讯坐标转换 并发 5/s
     * @param address
     * @author dozen.zhang
     * @return
     */
    public static String TencentGeoCoder(String address,String key){
        logger.debug("传输参数：address："+address+"  key"+key);
        String url="http://apis.map.qq.com/ws/geocoder/v1/"; //服务地址
               key="&key="+key;//key
        String param="address="+address+key;//服务参数
        String result= HttpRequestUtil.sendGet(url,param);  //拿到返回结果
        Map<String ,Object> re= JsonUtil.fromJson(result,Map.class);
        return  judge(re);
    }

    public static String judge(Map<String, Object> re){
        if(CastUtil.toString(re.get("status")).equals("0") ){  //判断是否成功
            String r=CastUtil.toString(re.get("result"));
            Map<String ,Object> res= JsonUtil.fromJson(r,Map.class);
            return CastUtil.toString(res.get("location"));  //获取Location
        }else {
            return null;
        }
    }


}






















