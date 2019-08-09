package com.dozenx.web.util;

import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.Config;
import com.dozenx.common.util.FastYml;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.common.util.db.MysqlUtil;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


/**
 * Created by dozen.zhang on 2016/12/5.
 */
public class ConfigUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigUtil.class);

    public static Properties properties = new Properties();

    /**
     * 根据name获取配置项值
     *
     * @param name
     * @return
     */
    public static String getConfig(String name) {
//        Object object = BeanUtil.getBean("sysConfigService");
        if (StringUtil.isBlank(name)) {
            logger.error("ConfigUtil.getConfig 参数不能为空");
            return null;
            // throw new Exception("config param is null");
        }
        //直接从配置文件中读取
        if (properties.size() == 0) {
            new ConfigUtil().loadProperty();
        }
        String value = (String) properties.get(name);
        if (StringUtil.isNotBlank(value)) {
            return value;
        }
        //如果配置文件中没有这一项目
        //从缓存中读取
       /*  value = (String) CacheUtil.getInstance().readCache(name, String.class);
        if (StringUtil.isBlank(value)) {
            // Object object = BeanUtil.getBean("sysConfigService");
            //如果缓存中没有这个项目就从数据库中读取 好像顺序反了,应该先从缓存中读取
            //读不到在从配置文件或者数据库中读取
            //虽然每次都去缓存中去获取不太好,但是貌似没有更好的方式了,因为缓存能实现唯一修改后立即生效
            //配置文件的话 如果多台服务器还要一个一个的修改台麻烦了,数据库只要改一个地方就可以了
                //从数据库中改了之后立马 更新缓存 跟新了缓存之后 整个系统就都正常了
            logger.error("配置信息未找到:"+name);
            return null;
//                if (config != null) {
//                    value = config.getValue();
//                    CacheUtil.getInstance().writeCache(name, value);
//                    return value;
//                } else {
//                    return null;
//                }
        } else {

            return value;
        }*/
        logger.info("properties size:"+properties.size());
        return value;
    }


    public void loadProperty() {//其实此种方式并不合适从properties 中读取配置,应为spring 已经支持了http远程加载配置文件的方式,那么此种方式在通过http请求就显得多余了,此种自定义Properties加载方式目前唯一的好处是可以从数据库中读取配置信息 或者可以通过加密的方式获取配置信息

//        logger.info("" + properties.size());
        //  if (StringUtils.isEmpty(url)) return;
//        logger.debug("loading remote properties:" + url);
        //String content =  HttpRequestUtil.sendGet(url,null);

        //===========加载主目录webroot下所有properties 文件==================
        List<HashMap<String, String>> propertiesList = new ArrayList<HashMap<String, String>>();
        //首先创建properties对象 //首先加载主目录下的main.properties配置文件
        properties = new Properties(); //PropertiesUtil.load("main.properties");


        //尝试着从application.yml中读取文件

        InputStream inputStream = Config.class.getResourceAsStream("/application.yml");
        if (inputStream != null) {
            try {
                Properties ymlProperties = new FastYml().readStreamAsProperties(inputStream);
                properties.putAll(ymlProperties);
            } catch (Exception e) {

            }
        }

        //查找底下的所有properties文件 不进入文件夹 说明所有的配置文件都必须要拷贝到WEBROOT目录下
        List<File> files = com.dozenx.common.util.FileUtil.listFile(PathManager.getInstance().getClassPath().toFile(), false);
        if (files != null)
            for (File file : files) {
                try {
                    if (file.getName().endsWith(".properties")) {
                        logger.debug("loading from outside properties");
                        properties.load(new FileInputStream(file));
                    } else if (file.getName().endsWith(".yml")) {
                        logger.debug("properties size"+properties.size());
                        logger.debug("loading from outside yml");
                        Properties ymlProperties = new FastYml().readAsProperties(file);
                        properties.putAll(ymlProperties);
                        logger.debug("properties size"+properties.size());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //need to test if override add the add File content
                // properties.putAll(properties);
            }
        //  properties=PropertiesUtil.load("main.properties");
        // this.properties = propertiesFromFile;
        String readFrom = (String) properties.get("readfrom");
        if (StringUtil.isNotBlank(readFrom)) {
            String[] readFromAry = readFrom.split(",");//一般有两个值config 或者db config 表示从properties 文件获取 db表示从数据库获取

            for (String s : readFromAry) {
                if (s.equals("config")) {
                    String basePropertiesPath = (String) properties.get("basePropertiesPath");
                    String addPropertiesPath = (String) properties.get("addPropertiesPath");
                    try {
                        //===========加载主目录basePropertiesPath下所有properties 文件==================
                        //优先加载properties 文件夹
                        File basePropertiesFolder = PathManager.getInstance().getClassPath().resolve(basePropertiesPath).toFile();
                        if (basePropertiesFolder != null && basePropertiesFolder.exists()) {
                            files = com.dozenx.common.util.FileUtil.listFile(basePropertiesFolder);
                            if (files != null)
                                for (File file : files) {

                                    if (file.getName().endsWith(".properties"))
                                        properties.load(new FileInputStream(file));
                                    //need to test if override add the add File content
                                    // properties.putAll(properties);
                                }
                        }

                        //===========加载主目录addPropertiesPath下所有properties 文件==================
                        if (StringUtil.isNotBlank(addPropertiesPath)) {
                            File addPropertiesFolder = PathManager.getInstance().getClassPath().resolve(addPropertiesPath).toFile();
                            if (addPropertiesFolder != null && addPropertiesFolder.exists()) {
                                //先从主文件获取配置文件
                                // String basePropertiesPathReal = PathManager.getInstance().getClassPath().resolve(basePropertiesPath).toString();

                                //其次加载beta alpha 文件夹
                                files = com.dozenx.common.util.FileUtil.listFile(addPropertiesFolder);
                                for (File file : files) {

                                    logger.debug("begin load properties:" + file.getAbsolutePath() + file.getName());
                                    if (file.getName().endsWith(".properties"))
                                        properties.load(new FileInputStream(file));
                                    //need to test if override add the add File content
                                    // properties.putAll(properties);
                                }
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (s.equals("db")) {
                    try {
                        MysqlUtil mysqlUtil = new MysqlUtil();
                        // Connection con = mysqlUtil.getConnection(this.getDriver(),this.getUser(), this.getPassword(), this.getUrl());
                        String driver = (String) properties.get("properties.db.jdbc.driver");
                        String user = (String) properties.get("properties.db.jdbc.user");
                        String pwd = (String) properties.get("properties.db.jdbc.password");
                        String url = (String) properties.get("properties.db.jdbc.url");
                        Connection con = mysqlUtil.getConnection(driver, user, pwd, url);
                        propertiesList = mysqlUtil.executeQuery(con, "select `key`,`value` from sys_config");
                        for (HashMap record : propertiesList) {
                            properties.put(MapUtils.getString(record, "key").trim(), MapUtils.getStringValue(record, "value").trim());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                    //说明是zookeeper配置文件读取方式
                } else if (s.equals("db")) {
                    //else if(s.equals("zookeeper")){
                    //=========读取zookeeper 配置文件 ip 端口 节点===============
                    String ip = (String) properties.get("properties.zookeeper.ip");
                    //  String port = (String)properties.get("port");
                    String path = (String) properties.get("properties.zookeeper.path");


                    //   zk = connectServer(ip,path);
                    //从zk从获取所有配置参数
                }


                // }
            }
        }
        //首先从指定的配置文件读取排位置信息
        //然后区分是数据库读取还是配置文件读取
        //如果是数据库读取则去数据库读取
        //如果是配置文件读取知道对应的配置文件目录,读取线面所有配置信息

//        if (properties.size() == 0) {
//            File file = PathManager.getInstance().getClassPath().resolve("properties/jdbc.properties").toFile();
//            String content = null;
//            try {
//                content = FileUtil.readAsString(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            logger.debug("remote properties conent:" + content);
//            String[] lines = content.replaceAll("\r", "").split("\n");
//            for (String line : lines) {
//                line = line.trim();
//                if (line.startsWith("#"))
//                    continue;
//                if (!StringUtils.isEmpty(line)) {
//                    String[] arr = line.split("=");
//                    properties.put(arr[0].trim(), arr[1].trim());
//                }
//            }
//        }

        //打印所有的properties

//        for (String key : properties.stringPropertyNames()) {
//            logger.debug(key + "=" + properties.getProperty(key));
//        }
        logger.info((String)properties.get("test"));
        ConfigUtil.properties = properties;
    }


}
