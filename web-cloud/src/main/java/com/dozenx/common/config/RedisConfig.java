package com.dozenx.common.config;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author dozen.zhang
 *
 */
public class RedisConfig {
    
    /**
     *是否启用 
     */
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    /**
     * Redis服务器IP
     */
    private String addr;
 
    /**
     * Redis的端口号
     */
    private int port;

    /**
     * 访问密码
     */
    private String auth;

    /**
     * 可用连接实例的最大数目，默认值为8；
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     */
    private int maxActive;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
    private int maxIdle;

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
     */
    private int maxWait;

    /**
     * 期限
     */
    private int timeout;

    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    private boolean testOnBorrow;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * json 转换
     * @author dozen.zhang
     *
     */
    public static class Handler implements JsonSerializer<RedisConfig>, JsonDeserializer<RedisConfig> {

      
        /**
         * @param json 参数
         * @param typeOfT 参数
         * @param context 上下文
         * @return RedisConfig
         * @throws JsonParseException 抛出异常
         */
        public RedisConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            RedisConfig redisConfig = new RedisConfig();
            JsonObject jsonObject = json.getAsJsonObject();
            redisConfig = context.deserialize(json, RedisConfig.class);
            return redisConfig;
        }

     
        /**
         * @param src 参数
         * @param typeOfT 参数
         * @param context 上下文
         * @return JsonElement 抛出异常
         */
        public JsonElement serialize(RedisConfig src, Type typeOfT, JsonSerializationContext context) {
           Gson gson =new Gson();
            return gson.toJsonTree(src);
        }

    }
}
