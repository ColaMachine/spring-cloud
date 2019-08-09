package com.dozenx.common.util;

import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.FileNameSelector;
import com.dozenx.common.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties confProperties;

	{
		if(confProperties == null) {
	    	confProperties = new Properties();
	    }

		InputStream in = ClassLoader.getSystemResourceAsStream("properties/config.properties");
		if(in == null) {
			in = ClassLoader.getSystemResourceAsStream("resources/properties/app-config.properties");
		}

		try {
			confProperties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param filePath the path relative to web system
	 * @return
     */
	public static Properties load(String filePath){

			 confProperties = new Properties();

/*
		InputStream in = ClassLoader.getSystemResourceAsStream(filePath);
		if(in == null) {
			in = ClassLoader.getSystemResourceAsStream("resources/"+filePath);
		}*/
		File file = PathManager.getInstance().getClassPath().resolve(filePath).toFile();
		if(!file.exists()) {
			System.out.println("加载properties的路径有问题:" + file.getAbsolutePath());
			
		}
		try {
			confProperties.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return confProperties;
	}
	/**
	 *
	 * @param file the path relative to web system
	 * @return
	 */
	public static Properties load(File file){
		Properties confProperties = new Properties();
		if(!file.exists()) {
			System.out.println("加载properties的路径有问题:" + file.getAbsolutePath());
		}
		try {
			confProperties.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return confProperties;
	}
	public static void init() throws Exception {
		File rootDirectory = new File(PropertiesUtil.class.getClassLoader().getResource("properties").getPath());
	    File[] propertiesFiles = rootDirectory.listFiles(new FileNameSelector("properties"));



	    if(confProperties == null) {
	    	confProperties = new Properties();
	    }
		for(File file : propertiesFiles) {
			FileInputStream fis = new FileInputStream(file);
			confProperties.load(fis);
			fis.close();
		}


		 rootDirectory = new File(PropertiesUtil.class.getClassLoader().getResource("/").getPath());
		 propertiesFiles = rootDirectory.listFiles(new FileNameSelector("properties"));

		for(File file : propertiesFiles) {
			FileInputStream fis = new FileInputStream(file);
			confProperties.load(fis);
			fis.close();
		}
	}
	
	public static Properties getProperties() throws Exception{
		Properties props = new Properties();

		InputStream in = ClassLoader.getSystemResourceAsStream("properties/config.properties");
		if(in == null){
			in = ClassLoader.getSystemResourceAsStream("resources/properties/config.properties");
		}

		props.load(in);
		in.close();
		return props;
	}
	
	public static void clear(){
		confProperties.clear();
		confProperties = null;
	}
	
	public static String get(String key) throws Exception{
		if(confProperties == null){
			init();
		}
		return confProperties.getProperty(key);
	}

	public static String get(String key,String file) {
		if(confProperties == null){
			confProperties= load(file);
		}
		String value = confProperties.getProperty(key);
		if(StringUtil.isBlank(value )){
			confProperties.putAll(load(file));
		}
		return confProperties.getProperty(key);
	}

	public static void  add(HashMap data) throws Exception{
		if(confProperties == null){
			init();
		}
		if(data==null || data.size()==0)
			return;
		Iterator<Map.Entry> it= data.keySet().iterator();
		while(it.hasNext()){
			Map.Entry entry  = (Map.Entry)it;
			confProperties.put(entry.getKey(),entry.getValue());
		}
		//confProperties.

	}

	public static void main(String[] args) throws Exception {
		init();
		System.out.println(PropertiesUtil.get("server.portal.hostname"));
	}
}
