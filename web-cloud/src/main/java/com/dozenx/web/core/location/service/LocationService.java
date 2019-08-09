/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2018年2月6日 上午10:26:51
* 创建作者：尤小平
* 文件名称：IndustryService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.dozenx.web.core.location.service;

import com.alibaba.fastjson.JSON;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.util.RedisUtil;
import com.dozenx.web.core.RedisConstants;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.common.util.CastUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("locationService")
public class LocationService extends BaseService {
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * IndustryApiService
     */
    @Resource
    private LoctionApiService loctionApiService;

    /**
     * 获取省地区信息
     * @return list
     * @author 周颖
     * @throws Exception
     * @date 2017年1月22日 下午2:26:43
     */
    String provinceRedisKey = RedisConstants.LOCATION + "1";
    public List<Map<String, Object>> getProvinces() throws Exception{
        String redisKey = provinceRedisKey;
        return this.getChildLocations(redisKey);//返回list
    }

    public String  getProvincesJsonStr() throws Exception{
        String redisKey = RedisConstants.LOCATION_CHILDS+1;
        return getChildLocationsJsonStr(redisKey);//返回list
    }

    /**
     * 获取市地区信息
     * @param provinceId 省id
     * @return list
     * @author 周颖
     * @throws Exception
     * @date 2017年1月22日 下午2:26:43
     */
    public  List<Map<String,Object>> getCities(String provinceId) throws Exception{
        String redisKey = RedisConstants.LOCATION + provinceId;//生成redisKey
        return this.getChildLocations(redisKey);//只返回id+name的list
    }

    public  String getCitiesJsonStr(String provinceId) throws Exception{
        String redisKey = RedisConstants.LOCATION_CHILDS + provinceId;//生成redisKey
        return getChildLocationsJsonStr(redisKey);//只返回id+name的list
    }

    /**
     * 获取区县地区信息
     * @param cityId 市id
     * @return list
     * @author 周颖
     * @throws Exception
     * @date 2017年1月22日 下午2:26:43
     */
    public  List<Map<String,Object>> getAreas(String cityId) throws Exception{
        String redisKey = RedisConstants.LOCATION + cityId;//生成redisKey
        return this.getChildLocations(redisKey);//只返回id+name的list
    }


    public  String getAreasJsonStr(String cityId) throws Exception{
        String redisKey = RedisConstants.LOCATION_CHILDS + cityId;//生成redisKey
        return this.getChildLocationsJsonStr(redisKey);//只返回id+name的list
    }

    /**
     * 获取子地区集合
     * @param redisKey redisKey
     * @return 子地区集合
     * @throws Exception 异常
     * @author 许小满
     * @date 2017年11月3日 下午12:05:26
     */
    private List<Map<String, Object>> getChildLocations(String redisKey){
        List<Map<String, Object>> locationList = getChilds(redisKey);//从redis 中区取
        if(locationList != null){//不为空时，返回地区数据
            return locationList;
        }
       /* boolean isCached = loctionApiService.isCached();//判断地区信息是否缓存
        if(isCached){//有缓存
            return new ArrayList<Map<String, Object>>();//直接返回空集合
        }*/
        loctionApiService.cache();//重新进行地区缓存

        locationList = getChilds(redisKey);//重新获取地区数据
        if(locationList != null){//不为空时，返回地区数据
            return locationList;
        }
        return new ArrayList<Map<String, Object>>();//直接返回空集合
    }


    private String getChildLocationsJsonStr(String redisKey){
      String locationListStr = RedisUtil.get(redisKey);

        if(!StringUtil.isBlank(locationListStr)){//不为空时，返回地区数据
            return locationListStr;
        }
       /* boolean isCached = loctionApiService.isCached();//判断地区信息是否缓存
        if(isCached){//有缓存
            return new ArrayList<Map<String, Object>>();//直接返回空集合
        }*/
        loctionApiService.cacheJsonStr();//重新进行地区缓存

         locationListStr = RedisUtil.get(redisKey);//重新获取地区数据
        if(!StringUtil.isBlank(locationListStr)){//不为空时，返回地区数据
            return locationListStr;
        }
        return "{}";//直接返回空集合
    }

    /**
     * 获取单个地区
     * @param redisKey redisKey
     * @return 地区集合
     * @author 许小满
     * @date 2017年11月3日 上午11:16:33
     */
    private List<Map<String, Object>> getChilds(String redisKey) {
        List<String> redisList = RedisUtil.hmget(redisKey, "childs");//获取key对应的值
        if(redisList == null || redisList.size() <=0 ){
            return null;
        }
        List<Map<String,Object>> locationList = this.formatChild(redisList);//解析map
        return locationList;//只返回id+name的list
    }

    /**
     * 只返回id+name
     * @param redisList 缓存list
     * @return map
     * @author 许小满
     * @date 2017年11月3日 上午11:16:33
     */
    @SuppressWarnings("unchecked")
    private List<Map<String,Object>> formatChild(List<String> redisList) {
        String childs = redisList.get(0);//子地区json格式字符串
        if(StringUtil.isBlank(childs)){//如果为空，流程结束
            return null;
        }
        List<Map<String,Object>> childList = JSON.parseObject(childs, List.class);//字符串转成集合
        sort(childList);//排序
        int maxSize = childList.size();//集合最大个数

        List<Map<String,Object>> loactionList = new ArrayList<Map<String,Object>>(maxSize);
        for(int i=0; i<maxSize; i++){//循环集合
            Map<String,Object>cacheLocationMap = childList.get(i);//缓存中的地区map
            Long id = CastUtil.toLong(cacheLocationMap.get("id"));//地区id
            String areaName = StringUtil.defaultString((String)cacheLocationMap.get("name"));//地区名称

            Map<String, Object> locationMap = new LinkedHashMap<String,Object>(2);
            locationMap.put("id", id);//地区id
            locationMap.put("areaName", areaName);//地区名称
            loactionList.add(locationMap);
        }
        return loactionList;
    }

    /**
     * 按地区编号从小到大排序(采用冒泡排序)
     * @param locationMapList 地区map
     * @author 许小满
     * @date 2016年11月11日 下午4:10:11
     */
    private static void sort(List<Map<String, Object>> locationMapList){
        int maxSize = locationMapList.size();
        for(int i=1; i<maxSize; i++){
            for(int j=0; j<maxSize-i; j++){
                Map<String,Object> curMap = locationMapList.get(j);//地区map
                Map<String,Object> nextMap = locationMapList.get(j+1);//地区map

                String curCode = StringUtil.defaultString((String)curMap.get("code"), "zzz");//地区编号，为空时赋值为zzz，为了排序靠后
                String nextCode = StringUtil.defaultString((String)nextMap.get("code"), "zzz");//地区编号，为空时赋值为zzz，为了排序靠后

                if((curCode.compareTo(nextCode)) > 0){//位置互换
                    locationMapList.set(j, nextMap);
                    locationMapList.set(j+1, curMap);
                }
            }
        }
    }
	//地区名字id 翻译 map
    public static  Map<Long ,String> idNameMap=null;
    public   String getNameById(Long id){
        if(idNameMap==null||idNameMap.size()==0){
            idNameMap=new HashMap<>();//防止一直报错
            loctionApiService.cacheJsonStr();//重新进行地区缓存
        }
        return idNameMap.get(id);
    }

    public static  Map<String,Long > nameIdMap=null;
    public   Long getIdByName(String name){
        if(nameIdMap==null||nameIdMap.size()==0){
            nameIdMap=new HashMap<>();//防止一直报错
            loctionApiService.cacheJsonStr();//重新进行地区缓存
        }
        return nameIdMap.get(name);
    }
}