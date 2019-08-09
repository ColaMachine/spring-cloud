package com.dozenx.web.util;

import com.alibaba.fastjson.JSON;
import com.dozenx.common.config.CacheConfig;
import com.dozenx.common.config.Config;
import com.dozenx.web.core.RedisConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.google.gson.Gson;

public class CacheUtil {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory
            .getLogger(CacheUtil.class);

    public static void set(String key, String value, int sec) {
        getInstance().writeCache(key,value,sec);
    }

    public static class CacheInner {
        /**
         * new一个对象
         */
        public static final CacheUtil cacheManager = new CacheUtil();
    }

    /**
     * @return CacheUtil
     */
    public static CacheUtil getInstance() {
        return CacheInner.cacheManager;
    }

    /**
     * 
     * @param key
     *            参数
     * @param c
     *            参数
     * @return Object
     */
    public Object readCache(String key, Class c) {
        // System.out.println("readCache key:"+key);
        // 先冲本地服务器获取缓存
        CacheConfig cacheConfig = Config.getInstance().getCache();
        Object object = null;
        if (cacheConfig.getEhcache().isEnable()){
            object = readLocalCache(key);
        }

        if (cacheConfig.getRedis().isEnable()) {
            if (object == null) {
                object = readServerCache(key, c);
                if (object != null) {
                    if (cacheConfig.getEhcache().isEnable()){
                        writeLocalCache(key, object);
                    }
                    return object;
                } else {
                    return null;
                }
            } else {
                return object;
            }
        } else {
            return object;
        }
    }

    public static String  get(String key) {
       return (String )getInstance().readCache(key,String.class);
    }

    /**
     * @param key
     *            参数
     * @param value
     *            参数
     */
    public void writeCache(String key, Object value) {
        // System.out.println("writeCache key:"+key);
        // 先保存本地缓存
        if (Config.getInstance().getCache().getEhcache().isEnable()){
            writeLocalCache(key, value);
        }
        // 再更新服务器缓存
        if (Config.getInstance().getCache().getRedis().isEnable()){
            writeServerCache(key, value);
        }
    }
    public void writeCache(String key, Object value,int seconds) {
        // System.out.println("writeCache key:"+key);
        // 先保存本地缓存
        if (Config.getInstance().getCache().getEhcache().isEnable()){
            writeLocalCache(key, value);
        }
        // 再更新服务器缓存
        if (Config.getInstance().getCache().getRedis().isEnable()){
            writeServerCache(key, value,seconds);
        }
    }
    /**
     * @param key
     *            参数
     * @param value
     *            参数
     */
    public void writeLocalCache(String key, Object value) {
        CacheManager manager = CacheManager.create();
        Cache cache = manager.getCache("kaqm");
        if (cache == null) {
            cache = new Cache("kaqm", 1000, false, false, 86400, 86400);
            manager.addCache(cache);
        }

        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * @param key
     *            参数
     * @return Object
     */
    public Object readLocalCache(String key) {
        CacheManager manager = CacheManager.create();
        Cache cache = manager.getCache("kaqm");
        if (cache == null) {
            return null;
        }
        Element element = cache.get(key);
        if (element != null){
            return element.getObjectValue();
        }
        else{
            return null;
        }
    }

    /**
     * @param key
     *            参数
     * @param value
     *            参数
     */
    public void writeServerCache(String key, Object value) {
//        Gson gson = new Gson();
        String json = JSON.toJSONString(value);
        RedisUtil.setex(key, json, RedisConstants.DAY_SECONDS);
//        Jedis jedis = RedisUtil.getJedis();
//        jedis.set(key, json);
//        RedisUtil.returnResource(jedis);
    }
    /**
     * @param key
     *            参数
     * @param value
     *            参数
     */
    public void writeServerCache(String key, Object value,int seconds) {
//        Gson gson = new Gson();
        String json = JSON.toJSONString(value);
        RedisUtil.setex(key, json,seconds);
//        Jedis jedis = RedisUtil.getJedis();
//        jedis.set(key, json);
//        RedisUtil.returnResource(jedis);
    }

    /*
     * public static Object ReadStrCache(String type, String key) { byte[]
     * persons = RedisUtil.getJedis().get((type + ":" + key).getBytes()); return
     * SerializeUtil.unserialize(persons); }
     */

    /**
     * @param key
     *            参数
     * @param c
     *            参数
     * @return Object
     */
    public Object readServerCache(String key, Class c) {
        String json = RedisUtil.get(key);
//        Gson gson = new Gson();
//        System.out.println(json);
        Object object = JSON.parseObject(json, c);
        return object;
    }

    /*
     * public static void WriteStrCache(String type, String key, String value) {
     * RedisUtil.getJedis().set(type + ":" + key, value,"NX","EX",600); } public
     * static String ReadStrCache(String type, String key) { return
     * RedisUtil.getJedis().get(type + ":" + key); }
     */

    /**
     * @param key
     *            参数
     */
    public void clearCache(String key) {
        // 先保存本地缓存
        // System.out.println("clearCache key:"+key);
        // 再更新服务器缓存

        if (Config.getInstance().getCache().getEhcache().isEnable()){
            clearLocalCache(key);
        }

        if (Config.getInstance().getCache().getRedis().isEnable()){
            clearServerCache(key);
        }
    }

    /**
     * @param key
     *            参数
     */
    public void clearLocalCache(String key) {
        CacheManager manager = CacheManager.create();
        Cache cache = manager.getCache("kaqm");
        if (cache == null) {
            cache = new Cache("kaqm", 1000, false, false, 86400, 86400);
            manager.addCache(cache);
        }

        cache.remove(key);
    }

    /**
     * @param key
     *            参数
     */
    public void clearServerCache(String key) {
        RedisUtil.del(key);
    }

}
