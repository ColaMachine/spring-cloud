/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2018年2月6日 上午10:24:13
* 创建作者：尤小平
* 文件名称：IndustryApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.dozenx.web.core.location.service;


import com.dozenx.common.util.*;
import com.dozenx.web.core.location.bean.Location;
import com.dozenx.web.core.location.dao.LocationMapper;
import com.dozenx.web.util.RedisUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.RedisConstants;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.util.ConfigUtil;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service(value = "loctionApiService")
public class LoctionApiService extends BaseService {
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(LoctionApiService.class);

    @Resource
    private LocationMapper locationMapper ;

    

    /**
     * 缓存地区信息
     * @author 周颖
     * @throws Exception
     * @date 2017年1月22日 上午10:05:34
     */
    public void cache(){
        Map<Long,Map<String,Object>> allLocationMap = getAllLocation();//全部地区
        String key;//获取地区key前缀
        Long id;//地区主键
        Long parentId;//父id
        Map<String,Object> locationMap;//地区map
        Map<String,Map<String,String>> cacheLocationMap = new LinkedHashMap<String,Map<String,String>>();
        for(Map.Entry<Long, Map<String,Object>> entry : allLocationMap.entrySet()){//循环map
            id = entry.getKey();//获取地区主键id
            locationMap = entry.getValue();//获取地区属性
            parentId = (Long) locationMap.get("parentId");//获取父id
            if(parentId == null){//如果为空，跳过本次循环
                continue;
            }
            key = RedisConstants.LOCATION + id;//rediskey
            Map<String,String> locationStrMap = toStringMap(locationMap, allLocationMap);//转成字符串map
            if(locationStrMap == null){//如果为空，跳过本次循环
                continue;
            }
            cacheLocationMap.put(key, locationStrMap);//保存 key-value  advert_location_31  :   {id:31,name code type fullName parentId}
        }
        RedisUtil.hmsetBatch(cacheLocationMap, RedisConstants.LOCATION_TIME);//redis缓存
    }


    public synchronized void cacheJsonStr(){
        //这里会导致再次启动系统就没有idNameMap值了
        String flag = RedisUtil.get(RedisConstants.LOCATION_CHILDS+1);
        if(StringUtil.isNotBlank(flag)){
            return ;
        }
        Map<Long,Location> allLocationMap = getAllLocationBean();//全部地区

        String key;//获取地区key前缀
        Long id;//地区主键
        Long parentId;//父id
       Location locationMap;//地区map
        Map<Long,List<Location>> parentId2ChildLocationsMap = new LinkedHashMap<Long,List<Location>>();

        //组装child数据

        for(Location location:getAllLocationList()){

            parentId = location.getParentId();//获取父id
            if(parentId == null){//如果为空，跳过本次循环
                continue;
            }
            List<Location> childList = parentId2ChildLocationsMap.get(parentId);
            if(childList!=null ){
                childList.add(location);
            }else{
                childList =new ArrayList<Location>();
                parentId2ChildLocationsMap.put(parentId,childList);
            }
        }
     /*   for(Map.Entry<Long, Location> entry : allLocationMap.entrySet()){//循环map
            id = entry.getKey();//获取地区主键id
            locationMap = entry.getValue();//获取地区属性
            parentId = locationMap.getParentId();//获取父id
            if(parentId == null){//如果为空，跳过本次循环
                continue;
            }
            List<Location> childList = parentId2ChildLocationsMap.get(parentId);
            if(childList!=null ){
                childList.add(locationMap);
            }else{
                childList =new ArrayList<Location>();
                parentId2ChildLocationsMap.put(parentId,childList);
            }

        }*/
        //将childs json 数据存入到redis
        for(Map.Entry<Long, List<Location>> entry : parentId2ChildLocationsMap.entrySet()){//循环map
            id = entry.getKey();//获取地区主键id
            List<Location> list  = entry.getValue();//获取地区属性
            RedisUtil.setex(RedisConstants.LOCATION_CHILDS+id,JsonUtil.toJsonString(list),RedisConstants.DAY_SECONDS*3);

        }


    }

    /**
     * 将Map<String,Object> 转为 Map<String,String> 并填充子地区
     * @param locationMap 地区map
     * @param allLocationMap 全部地区map
     * @return map
     * @author 许小满
     * @date 2016年11月3日 下午12:51:25
     */
    private static Map<String,String> toStringMap(Map<String,Object> locationMap, Map<Long,Map<String,Object>> allLocationMap){
        Long id = (Long)locationMap.get("id");//地区id
        String name = (String)locationMap.get("name");//地区名称
        String fullName = (String)locationMap.get("fullName");//地区全路径
        String code = (String)locationMap.get("code");//地区编号 -- 用于排序
        Long parentId = (Long)locationMap.get("parentId");//父id
        String type = (String)locationMap.get("type");//地区级别：PROVINCE 省、CITY 市、 COUNTY 区县

        if(id == null || StringUtil.isBlank(name) || parentId == null){
//            logger.debug("错误：存在空的记录  id[" + id + "] : name[" + name + "] : parentId[" + parentId + "]");
            return null;
        }

        Map<String,String> locationStrMap = new LinkedHashMap<String,String>(5);
        locationStrMap.put("id", id.toString());//地区id
        locationStrMap.put("name", StringUtil.defaultString(name));//地区名称
        locationStrMap.put("fullName", StringUtil.defaultString(fullName));//地区全路径
        locationStrMap.put("code", StringUtil.defaultString(code));//地区编号 -- 用于排序
        locationStrMap.put("parentId", parentId.toString());//父id
        locationStrMap.put("type", type);//地区级别：PROVINCE 省、CITY 市、 COUNTY 区县
        /* 配置子地区 */
        String childs = configChilds(id, type, allLocationMap);
        if(StringUtil.isNotBlank(childs)){
            locationStrMap.put("childs", childs);//子地区集合
        }
        return locationStrMap;
    }

    /**
     * 配置子地区
     * @param id 地区id
     * @param type 地区级别：PROVINCE 省、CITY 市、 COUNTY 区县
     * @param allLocationMap 全部地区map
     * @return json格式的子地区集合字符串
     * @author 许小满
     * @date 2017年11月3日 上午9:45:40
     */
    private static String configChilds(Long id, String type, Map<Long,Map<String,Object>> allLocationMap){
        if(StringUtil.isBlank(type) || type.equals("COUNTY")){//type为空或为区县时，跳过
            return null;
        }
        List<Map<String,Object>> childList = new ArrayList<Map<String, Object>>();//地区map
        Map<String,Object> childMap = null;//子地区map
        for(Map.Entry<Long, Map<String,Object>> entry : allLocationMap.entrySet()){//循环map
            Map<String,Object> locationMap = entry.getValue();//获取地区属性
            Long parentId = CastUtil.toLong(locationMap.get("parentId"));//父id
            if(parentId.equals(id)){
                childMap = new LinkedHashMap<String,Object>(3);//实例化子地区map
                childMap.put("id", locationMap.get("id"));//地区id
                childMap.put("name", locationMap.get("name"));//地区名称
                childMap.put("code", locationMap.get("code"));//地区编号，用于排序
                childList.add(childMap);
            }
        }
        return JsonUtil.toJsonString(childList);
    }



    /**
     * 判断是否已经缓存过
     * @return true 已缓存、false 未缓存
     * @author 许小满
     * @date 2017年11月3日 上午10:40:24
     */

    public static final String BEIJING_LOCATION_KEY= RedisConstants.LOCATION + "2";
    public static boolean isCached() {
        boolean isCached = RedisUtil.exists(BEIJING_LOCATION_KEY);//判断地区信息是否缓存 以北京为例
        return isCached;
    }






    /**
     * 获取全部地区信息 从数据库 或者其他接口获取数据 然后提取关心的字段形成 map放到allLocationmap
     * @author 周颖
     * @return map
     * @date 2017年1月22日 上午10:05:34
     */
    public Map<Long,Map<String,Object>> getAllLocation(){
        //String url = ConfigUtil.getConfig("dbc_getlocationlist_url");//获取数据中心地区信息接口地址
        Map<String,Object> param = new HashMap<String,Object>();//请求参数map
        param.put("status", 1);//状态关系 1 正常 9 作废
//        String paramString = null;
//        try {
//            paramString = "&params="+ URLEncoder.encode(JsonUtil.toJsonString(param), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
////            ErrorUtil.printException(e, logger);
//        }
       // String result = HttpRequestUtil.sendGet(url, paramString);//发送get请求
//        Map<String, Object> returnMap//JsonUtil.toJavaBean(result, Map.class);//转成map
        List<Map<String,Object>> locationList =   locationMapper.selectAll();;//获取数据
        Map<Long,Map<String,Object>> allLocationMap = new LinkedHashMap<Long,Map<String,Object>>();
        LocationService.idNameMap = new HashMap<Long ,String>();
        Map<String, Object> locationMap;

        for(Map<String,Object> map : locationList){


            Long id = CastUtil.toLong(map.get("id"));//主键id
            if(id == null){//如果为空 继续
                continue;
            }
            Long parentId = CastUtil.toLong(map.get("parent_id"));//父id为空 继续
            if(parentId == null){
                logger.debug("错误：地区id[" + id + "]对应的parentId为空！");
                continue;
            }
			//在内存中维护一个快速名字id 对照表 2018年2月27日13:59:38
            LocationService.idNameMap.put(id,(String) map.get("area_name"));
            locationMap = new LinkedHashMap<String,Object>(5);
            locationMap.put("id", id);
            locationMap.put("name", MapUtils.getStringValue(map,"area_name"));//地区名称
            locationMap.put("code", MapUtils.getStringValue(map,"area_cn_code"));//地区编号，用于排序
            locationMap.put("type", MapUtils.getStringValue(map,"area_type"));//地区级别：PROVINCE 省、CITY 市、 COUNTY 区县
            locationMap.put("parentId", parentId);//父id
            allLocationMap.put(id, locationMap);
        }
        this.configFullName(allLocationMap);//配置地区全路径
        return allLocationMap;
    }
   public static List<Location> allLocationList=new ArrayList<Location>();


    public static Map<Long,Location> allLocationBeanMap = new LinkedHashMap<Long,Location>();//方便快速根据id查询
    public List<Location > getAllLocationList(){
        return allLocationList;
    }
    public Map<Long,Location> getAllLocationBean(){
        allLocationList.clear();
        Map<String,Object> param = new HashMap<String,Object>();//请求参数map
        param.put("status", 1);//状态关系 1 正常 9 作废
        List<Map<String,Object>> locationList =   locationMapper.selectAll();;//获取数据


       // Map<Long,Location> allLocationBeanMap = new LinkedHashMap<Long,Location>();//方便快速根据id查询

   //     List<Location > allLocationList =new ArrayList<Location>();//这个是方便之后塞入redis的数据不会乱序  方便数据有序排列

    //    Map<Long,Location> hasChildLocationBeanMap = new LinkedHashMap<Long,Location>();
        LocationService.idNameMap = new HashMap<Long ,String>();
        LocationService.nameIdMap = new HashMap<String,Long >();
        Map<String, Object> locationMap;

        for(Map<String,Object> map : locationList){


            Long id = CastUtil.toLong(map.get("id"));//主键id
            if(id == null){//如果为空 继续
                continue;
            }
            Long parentId = CastUtil.toLong(map.get("parent_id"));//父id为空 继续
            if(parentId == null){
                logger.debug("错误：地区id[" + id + "]对应的parentId为空！");
                continue;
            }
            //在内存中维护一个快速名字id 对照表 2018年2月27日13:59:38
            LocationService.idNameMap.put(id,(String) map.get("area_name"));
            LocationService.nameIdMap.put((String) map.get("area_name"),id);
            Location location  = new Location();
            location.setId(id);
            location.setAreaName(MapUtils.getStringValue(map,"area_name"));
            location.setCode(MapUtils.getStringValue(map,"area_cn_code"));
            location.setType(MapUtils.getStringValue(map,"area_type"));
            location.setParentId(parentId);

            allLocationBeanMap.put(id, location);
            allLocationList.add(location);

        }
        this.configFullNameBean(allLocationBeanMap);//配置地区全路径
        return allLocationBeanMap;
    }


    /**
     * 补全地区全称
     * @param allLocationMap 地区 得到 浙江省-杭州市-江干区这样的全部名称
     * @author 周颖
     * @date 2017年2月8日 上午10:03:26
     */
    private void configFullName(Map<Long,Map<String,Object>> allLocationMap){//配置完成后变成 id name code type parentId fullName
        for(Map.Entry<Long, Map<String,Object>> entry : allLocationMap.entrySet()){
            Map<String,Object> locationMap = entry.getValue();//地区map
            String type = (String)locationMap.get("type");//类别： PROVINCE 省、CITY 市、 COUNTY 区县
            if(StringUtil.isBlank(type)){
                logger.debug("错误：type 为空！");
                continue;
            }
            if(type.equals("COUNTRY")){//国家
                String countryName = (String)locationMap.get("name");//国家名称
                locationMap.put("fullName", StringUtil.defaultString(countryName));//设置地区全路径
            } else if(type.equals("PROVINCE")){//省
                String provinceName = (String)locationMap.get("name");//省名称
                locationMap.put("fullName", StringUtil.defaultString(provinceName));//设置地区全路径
            }else if(type.equals("CITY")){//市
                String cityName = (String)locationMap.get("name");//市名称

                Long provinceId = (Long)locationMap.get("parentId");//省id
                if(provinceId == null){
                    logger.debug("错误：市[" + cityName + "]对应的父id为空！");
                    continue;
                }
                Map<String,Object> provinceMap = allLocationMap.get(provinceId);//省map

                String provinceName = (String)provinceMap.get("name");//省名称

                locationMap.put("fullName", StringUtil.defaultString(provinceName) + StringUtil.defaultString(cityName));//设置地区全路径
            }else if(type.equals("COUNTY")){//区县
                String countyName = (String)locationMap.get("name");//区县名称

                Long cityId = (Long)locationMap.get("parentId");//市id
                if(cityId == null){
                    logger.debug("错误：区县[" + countyName + "]对应的父id为空！");
                    continue;
                }
                Map<String,Object> cityMap = allLocationMap.get(cityId);//市map
                if(cityMap == null){
                    logger.debug("错误：区县["+ countyName +"]--- 市[" + cityId + "]对应的cityMap为空！");
                    continue;
                }
                String cityName = (String)cityMap.get("name");//市名称

                Long provinceId = (Long)cityMap.get("parentId");//省id
                if(provinceId == null){
                    logger.debug("错误：市[" + cityName + "]对应的父id为空！");
                    continue;
                }
                Map<String,Object> provinceMap = allLocationMap.get(provinceId);//省map
                String provinceName = (String)provinceMap.get("name");//省名称

                locationMap.put("fullName", StringUtil.defaultString(provinceName) + StringUtil.defaultString(cityName) + StringUtil.defaultString(countyName));//设置地区全路径
            }else{
                logger.debug("错误：type[" + type + "]超出了范围[COUNTRY|PROVINCE|CITY|COUNTY].");
            }
        }
    }


    private void configFullNameBean(Map<Long,Location> allLocationMap){//配置完成后变成 id name code type parentId fullName
        for(Map.Entry<Long, Location> entry : allLocationMap.entrySet()){
            Location location = entry.getValue();//地区map
            String type = location.getType();//类别： PROVINCE 省、CITY 市、 COUNTY 区县
            if(StringUtil.isBlank(type)){
                logger.debug("错误：type 为空！");
                continue;
            }
            if(type.equals("COUNTRY")){//国家
                String countryName = location.getAreaName();//国家名称
                location.setFullName(StringUtil.defaultString(countryName));
            } else if(type.equals("PROVINCE")){//省

                String provinceName = location.getAreaName();//省名称
                location.setFullName(StringUtil.defaultString(provinceName));
            }else if(type.equals("CITY")){//市
                String cityName = location.getAreaName();//市名称

                Long provinceId = location.getParentId();//省id
                if(provinceId == null){
                    logger.debug("错误：市[" + cityName + "]对应的父id为空！");
                    continue;
                }
               Location  provinceBean = allLocationMap.get(provinceId);//省map

                String provinceName = provinceBean.getAreaName();//省名称
                location.setFullName(StringUtil.defaultString(provinceName) + StringUtil.defaultString(cityName));
            }else if(type.equals("COUNTY")){//区县
                String countyName = location.getAreaName();;//区县名称

                Long cityId = location.getParentId();//市id
                if(cityId == null){
                    logger.debug("错误：区县[" + countyName + "]对应的父id为空！");
                    continue;
                }
                Location  cityMap = allLocationMap.get(cityId);//市map
                if(cityMap == null){
                    logger.debug("错误：区县["+ countyName +"]--- 市[" + cityId + "]对应的cityMap为空！");
                    continue;
                }
                String cityName =cityMap.getAreaName();//市名称

                Long provinceId = cityMap.getParentId();//省id
                if(provinceId == null){
                    logger.debug("错误：市[" + cityName + "]对应的父id为空！");
                    continue;
                }
                Location provinceMap = allLocationMap.get(provinceId);//省map
                String provinceName = provinceMap.getAreaName();//省名称

                location.setFullName(StringUtil.defaultString(provinceName) + StringUtil.defaultString(cityName) + StringUtil.defaultString(countyName));//设置地区全路径
            }else{
                logger.debug("错误：type[" + type + "]超出了范围[COUNTRY|PROVINCE|CITY|COUNTY].");
            }
        }
    }

}