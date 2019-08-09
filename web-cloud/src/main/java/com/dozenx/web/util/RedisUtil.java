package com.dozenx.web.util;


import com.dozenx.common.config.Config;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;
@Component
public final class RedisUtil {


    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    /**
     * Redis服务器IP
     */
    private static String ADDR = null;// Config.getInstance().getCache().getRedis().getAddr();

    /**
     * Redis的端口号
     */
    private static int PORT = 6639;//Config.getInstance().getCache().getRedis().getPort();


    /**
     * Redis index
     */
    private static int INDEX = 1;


    /**
     * 访问密码
     */
    private static String AUTH = null;

    /**
     * 可用连接实例的最大数目，默认值为8；
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     */
    private static int MAX_ACTIVE = 8;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
    private static int MAX_IDLE = 8;

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
     */
    private static int MAX_WAIT = -1;
    /**
     * 连接超时
     */
    private static int TIMEOUT = 30;

    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    private static boolean TEST_ON_BORROW = true;
    /**
     * 声明
     */
    public static JedisPool jedisPool = null;

    public static String  PWD = null;
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            /** Redis服务器IP */
            ADDR = ConfigUtil.getConfig("spring.redis.host");// Config.getInstance().getCache().getRedis().getAddr();

            /** Redis的端口号 */
            String portStr = ConfigUtil.getConfig("spring.redis.port");

            if(StringUtil.isNotBlank( portStr) && StringUtil.checkNumeric(portStr)){
                PORT = Integer.valueOf(portStr);//Config.getInstance().getCache().getRedis().getPort();
            }
            String indexStr = ConfigUtil.getConfig("spring.redis.database");
            if(StringUtil.isNotBlank( indexStr) && StringUtil.checkNumeric(indexStr)){
                INDEX = Integer.valueOf(indexStr);//Config.getInstance().getCache().getRedis().getPort();
            }
            /** Redis index */

            /** 访问密码 */
            String pwd =ConfigUtil.getConfig("spring.redis.password");
            if(StringUtil.isNotBlank(pwd) && !pwd.equals("null")){
                AUTH = pwd;
            }else{

            }
            if(StringUtil.isBlank(ADDR)){
                ADDR = ConfigUtil.getConfig("cache.redis.ip");// Config.getInstance().getCache().getRedis().getAddr();
                /** Redis的端口号 */
                PORT = Integer.valueOf(ConfigUtil.getConfig("cache.redis.port"));//Config.getInstance().getCache().getRedis().getPort();
                /** Redis index */
                INDEX = Integer.valueOf(ConfigUtil.getConfig("cache.redis.database.index"));
                /** 访问密码 */
                AUTH = Config.getInstance().getCache().getRedis().getAuth();
            }

           // AUTH = ConfigUtil.getConfig("cache.redis.pwd");//Config.getInstance().getCache().getRedis().getPort();

            //Config.getInstance().getCache().getRedis().getAuth();

            /** 可用连接实例的最大数目，默认值为8；
             *如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
             */
            MAX_ACTIVE = Config.getInstance().getCache().getRedis().getMaxActive();

            /** 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。 */
            MAX_IDLE = Config.getInstance().getCache().getRedis().getMaxIdle();

            /** 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException； */
            MAX_WAIT = Config.getInstance().getCache().getRedis().getMaxWait();
            /** 连接超时 */
            TIMEOUT = Config.getInstance().getCache().getRedis().getTimeout();
            TEST_ON_BORROW = Config.getInstance().getCache().getRedis().isTestOnBorrow();
            JedisPoolConfig config = new JedisPoolConfig();
            // config.setMaxActive(MAX_ACTIVE);
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);

            config.setMaxWaitMillis(1000);
            // config.setSoftMinEvictableIdleTimeMillis();
            config.setMinEvictableIdleTimeMillis(3000);
            config.setSoftMinEvictableIdleTimeMillis(3000);
            //  config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);

            // logger.debug(String.format("初始化redis ADDR:%s"));
            //   jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            logger.info("connect to redis:"+ADDR+" port:"+PORT +" pwd:"+AUTH+ " index:" + INDEX);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH, INDEX);

            if (getJedis() == null) {
                logger.error("redis can't get jedis  redis启动失败");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return Jedis
     */
    private static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
              //  resource.auth(PWD);
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param jedis 释放jedis资源
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
            //  jedis.close();
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
  /*  public static String get(String key){
        String value = null;

      ;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();;
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource( jedis);
        }

        return value;
    }  */

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
   /* public static void set(String key,String value){

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();;
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource( jedis);
        }

    }  */

    /**
     * 设置值并且设置超时时间
     *
     * @param key
     * @return
     */
    public static void setex(String key, String value, int seconds) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, seconds, value);
            logger.debug("redis setex key:" + key + " seconds:" + seconds + " value:" + value);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();
            ;
            e.printStackTrace();
        } finally {
            //返还到连接池
            if(jedis!=null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static void del(String key) {
        if (StringUtil.isNotBlank(key)) {
            Jedis jedis = null;
//            boolean success = true;
            try {
                jedis = jedisPool.getResource();

                jedis.del(key);
                logger.debug("redis del key:" + key);
            } catch (Exception e) {
                //释放redis对象
//                if (jedis != null) {
//                    jedis.close();
//                    ;
//                }
                e.printStackTrace();
                throw e;
            } finally {
                if ( jedis != null) {
                    jedis.close();
                }
                //返还到连接池

            }
        }
    }


    public static Set<String> hkeys(String key) {
        Set<String> retValue = null;
//        boolean success = true;
        if (StringUtil.isNotBlank(key)) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                retValue= jedis.hkeys(key);
                logger.debug("Redis.hkeys : result({}).", retValue);
            } catch (Exception e) {
//                success = false;
//                if (jedis != null) {
//                    jedis.close();
//                    ;
//                }
                logger.error("redis", e);
                throw e;
            } finally {
                if ( jedis != null) {
                    jedis.close();
                }
            }
        }
        return retValue;
    }

    public static void expire(String key, int seconds) {
        if (StringUtil.isNotBlank(key)) {
            Jedis jedis = null;
//            boolean success = true;
            try {
                jedis = jedisPool.getResource();
                Long result = jedis.expire(key, seconds);
                logger.debug("Redis.expire result for key: key({}), result({}).", key, result);
            } catch (Exception e) {
//                success = false;
//                if (jedis != null) {
//                    jedis.close();
//                    ;
//                }
                logger.error("redis", e);
                throw e;
            } finally {
                if ( jedis != null) {
                    jedis.close();
                }
            }
        }
    }

	/*public static InputStream readProperties() throws FileNotFoundException {
        // Properties props = new Properties();
		InputStream in = ClassLoader.getSystemResourceAsStream(configFilePath);
		if (in == null) {
			try {
				String filename = new URI(AccessAuth.class.getClassLoader()
						.getResource(configFilePath).toString()).toString();
				File file = new File(filename.replace("file:", ""));
				// System.out.println("File: " + file.getAbsolutePath());
				in = new FileInputStream(file);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return in;
	}*/

    /**
     * hset + 还连接
     *
     * @param key
     * @param field
     * @param value
     * @author dozen.zhang
     */
    public static void hset(String key, String field, String value) {
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
            logger.debug("hset " + key + " field" + field + " value" + value);
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 强制归还资源的hget
     *
     * @param key
     * @param field
     * @author dozen.zhang
     */
    public static String hget(String key, String field) {
        Jedis jedis = null;
        String value = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            value = jedis.hget(key, field);

            logger.debug("redis hget " + key + " field" + field + " value" + value);
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//                ;
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 获取数据 归还资源
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
            logger.debug("redis get " + key + " value" + value);
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//                ;
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if ( jedis != null) {
                jedis.close();
            }
        }

        return value;
    }

    public static synchronized Jedis getResource() throws Exception {
        //logger.info("jedis pool:" + (pool == null));
        Jedis temp = null;
        for (int i = 0; i < 3; i++) {
            temp = jedisPool.getResource();

            if (temp != null) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //不处理
            }

        }

        if (temp == null) {
            throw new Exception("RedisUtil.getResource ,获取jedis 为null");
        }

        return temp;
    }

    public static void hdel(String key, String field) throws Exception {
        if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(field)) {
            Jedis jedis = null;
//            boolean success = true;
            try {
                jedis = getResource();
                long result = jedis.hdel(key, field);
                logger.debug("Redis.hdel {0} set: result({1}).", key, result);
            } catch (Exception e) {
//                success = false;
//                if (jedis != null) {
//                    jedis.close();
////	                pool.returnBrokenResource(jedis);
//                }
                logger.error("redis", e);
                throw e;
            } finally {
                if (jedis != null) {
                    jedis.close();
//	                pool.returnResource(jedis);
                }
            }
        }
    }

    /**
     * 获取数据 归还连接
     *
     * @param key
     * @return
     */
    private static void set(String key, String value) {
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            logger.debug("redis set {0} value {1}", key, value);
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if ( jedis != null) {
                jedis.close();
            }
        }

    }

    /**
     * 获取数据 归还连接
     *
     * @param key
     * @return
     */
    public static void setByteAry(String key, byte[] value) {
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key.getBytes(), value);

        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if ( jedis != null) {
                jedis.close();
            }
        }

    }


    /**
     * 获取数据 归还连接
     *
     * @param key
     * @return
     */
    public static byte[] getByteAry(String key, byte[] value) {
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key.getBytes());

        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if ( jedis != null) {
                jedis.close();
            }
        }

    }


    /*
         * 释放redis对象。
         */
    public static void returnBrokenResource(Jedis resource) {
        jedisPool.returnBrokenResource(resource);
    }

    public static void main(String args[]) {
        int i = 0;
        while (true) {
            try {
                System.out.println("begin");
                Jedis jedis = RedisUtil.getJedis();
                if (jedis != null) {
                    jedis.set("1", i++ + "");
                } else {
                    System.out.println("jedis is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(DateUtil.formatToString(new Date(), "yyyy-MM-dd hh:mm:ss"));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自增
     */


    public static Long incr(String key) throws Exception {
        Long value = null;
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = getResource();
            value = jedis.incr(key);
            logger.debug("redis incr " + key);
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
////                pool.returnBrokenResource(jedis);
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
//                pool.returnResource(jedis);
            }
        }

        return value;
    }

    public static Long incr(String key, int timeout) throws Exception {
        Long value = null;
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = getResource();
            value = jedis.incr(key);
            jedis.expire(key, timeout);
            logger.debug("redis incr " + key);
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
////                pool.returnBrokenResource(jedis);
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if ( jedis != null) {
                jedis.close();
//                pool.returnResource(jedis);
            }
        }

        return value;
    }


    /**
     * Hash键值 字段 get操作
     *
     * @param key    键
     * @param fields 字段(可多个)
     * @return 结果
     * @author 尤小平
     * @date 2018年2月6日 下午2:50:13
     */
    public static List<String> hmget(final String key, final String... fields) {
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            List<String> filedValueList = jedis.hmget(key, fields);
            filedValueList.removeAll(Collections.singleton(null)); // 移除所有的null元素
            return filedValueList;
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if ( jedis != null) {
                jedis.close();
            }
        }
    }



    /**
     * 批量Hash键值设置操作
     *
     * @param dataMap map
     * @param seconds 有效时间
     * @return 结果
     * @author 尤小平
     * @date 2018年2月6日 下午2:50:35
     */
    public static String hmsetBatch(Map<String, Map<String, String>> dataMap, Integer seconds) {
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            boolean secondsNotNull = seconds != null;
            for (Map.Entry<String, Map<String, String>> entry : dataMap.entrySet()) {
                String key = entry.getKey();
                Map<String, String> value = entry.getValue();
                if (StringUtil.isBlank(key) || value.size() == 0) {
                    logger.error("key or value 不能为空 "+key+" "+value);
                }

                //遍历删除值为null的元素

                MapUtils.removeNull(value);
                pipeline.hmset(key, value);
                if (secondsNotNull) {
                    pipeline.expire(key, seconds);
                }
            }
            pipeline.sync();
            closePipeline(pipeline);
            return null;
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 批量Hash键值设置操作
     *
     * @param dataMap map
     * @param seconds 有效时间
     * @return 结果
     * @author 尤小平
     * @date 2018年2月6日 下午2:50:35
     */
    public static String hmset(String key, Map<String, String> dataMap, Integer seconds) {
        logger.error("hmset key :"+key+" "+seconds+"s" );
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            boolean secondsNotNull = seconds != null;


            if (StringUtil.isBlank(key) || dataMap == null || dataMap.size() == 0) {
                logger.error("key or value 不能为空");
            } else {
                pipeline.hmset(key, dataMap);
                if (secondsNotNull) {
                    pipeline.expire(key, seconds);
                }
                pipeline.sync();
            }
            //遍历删除值为null的元素

            closePipeline(pipeline);
            return null;
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if ( jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 关闭 管道
     *
     * @param pipeline 管道
     * @author 尤小平
     * @date 2018年2月6日 下午2:50:56
     */
    private static void closePipeline(Pipeline pipeline) {
        try {
            if (pipeline == null) {
                return;
            }
            pipeline.close();
        } catch (Exception e) {
        }
    }

    /**
     * 判断keys是否存在
     *
     * @param key key
     * @return true 存在、false 不存在
     * @author 尤小平
     * @date 2018年2月6日 下午2:51:12
     */
    public static Boolean exists(final String key) {
        Jedis jedis = null;
//        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            Boolean isExists = jedis.exists(key);
            logger.debug("redis exists {0}  {1}", key, isExists);
            return isExists;
        } catch (Exception e) {
//            success = false;
//            if (jedis != null) {
//                jedis.close();
//            }
            logger.error("redis", e);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 加锁
     * author 王作品
     *
     * @param locaName       锁的key
     * @param acquireTimeout 获取超时时间
     * @param timeout        锁的超时时间
     * @return 锁标识
     */
    public static String lockWithTimeout(String locaName,
                                         long acquireTimeout, long timeout) {
        Jedis conn = null;
        String retIdentifier = null;
        try {
            // 获取连接
            conn = RedisUtil.getJedis();
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int) (timeout / 1000);
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {//当获取超时了 就返回null
                if (conn.setnx(lockKey, identifier) == 1) {
                    conn.expire(lockKey, lockExpire);//锁的超时时间一般设置为1秒
                    // 返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    logger.error("线程报错 +e " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    /**
     * 加锁
     * @param key
     * @param secs
     * @return
     */
    public static boolean lock(String key,int secs) {
        Jedis conn = null;
        String retIdentifier = null;
        try {
            // 获取连接
            conn = RedisUtil.getJedis();
            // 随机生成一个value

            // 锁名，即key值
            // 超时时间，上锁后超过此时间则自动释放锁


            if (conn.setnx(key, "1") == 1) {
                conn.expire(key, secs);//锁的超时时间一般设置为1秒
                // 返回value值，用于释放锁时间确认

                return true;
            }
            // 返回-1代表key没有设置超时时间，为key设置一个超时时间

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.error("线程报错 +e " + e.getMessage());
                Thread.currentThread().interrupt();
            }

        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            return false;
        }

    }

    /**
     * 释放锁
     * author 王作品
     *
     * @param lockName   锁的key
     * @param identifier 释放锁的标识
     * @return
     */
    public static boolean releaseLock(String lockName, String identifier) {
        Jedis conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = RedisUtil.getJedis();
            while (true) {
                // 监视lock，准备开始事务
                conn.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }

    /**
     * 释放锁
     * author 王作品
     *
     * @param key   锁的key
     * @param value 释放锁的标识
     * @return
     */
    public static void lpush(String key, String value) {
        Jedis conn = null;
        try {
            conn = RedisUtil.getJedis();
            conn.lpush(key, value);
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

    }

    public static String rpop(String key) {
        Jedis conn = null;
        try {
            conn = RedisUtil.getJedis();
            return conn.rpop(key);
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    /**
     * zadd
     * @param key
     * @param score
     * @param member
     */
    public static void zadd(String key,Long score,String member){
        Jedis conn = null;
        try {
            conn = getJedis();
             conn.zadd(key,score,member);
             logger.info("zadd key:"+key+" score:"+score+" member:"+member);
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

    }

    /**
     * zrange
     * @param key
     * @param startScore
     * @param endSocre
     * @return
     */
    public static Set<String>  zrange(String key,Long startScore,Long endSocre){
        Jedis conn = null;
        try {
            conn = getJedis();
            logger.info("zrange "+key+" "+startScore+" "+endSocre);
            return conn.zrange(key,startScore,endSocre);
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }
    /**
     * zrangebyscore
     * @param key
     * @param startScore
     * @param endSocre
     * @return
     */
    public static Set<String>  zrangebyscore(String key,Long startScore,Long endSocre){
        Jedis conn = null;
        try {
            conn = getJedis();
            logger.info("zrangebyscore "+key+" "+startScore+" "+endSocre);
            return conn.zrangeByScore(key,startScore,endSocre);
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    /**
     * zrange
     * @param key
     * @param member
     * @return
     */
    public static void  zrem(String key,String member){
        Jedis conn = null;
        try {
            conn = getJedis();
             conn.zrem(key,member);
        } catch (JedisException e) {
            logger.error("JedisException报错 +e " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

    }

    public static String printPoolStatus(){
        StringBuffer stringBuffer =new StringBuffer();
        stringBuffer.append("getNumWaiters:").append(jedisPool.getNumWaiters())
                .append("getNumActive:").append(jedisPool.getNumActive())
                .append("getNumIdle:").append(jedisPool.getNumIdle())
                .append("getMaxBorrowWaitTimeMillis:").append(jedisPool.getMaxBorrowWaitTimeMillis())
                .append("getMeanBorrowWaitTimeMillis:").append(jedisPool.getMeanBorrowWaitTimeMillis());
        logger.info(stringBuffer.toString());
        return stringBuffer.toString();

    }
}