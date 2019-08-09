package com.dozenx.common.util;


import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.Config;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

public final class StaticUtil {
    /**
     * 说明:
     * @return void
     * @author dozen.zhang
     * @date 2015年12月15日下午11:15:17
     */

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
    public static void main1(String args[]) throws Exception {
        setFinalStatic(Boolean.class.getField("FALSE"), true);

        System.out.format("Everything is %s", false); // "Everything is true"
    }
    public static void loadTo(Properties properties,Class a){
        Field[] fields = a.getDeclaredFields();
        try {
        for(int i=0;i<fields.length;i++){
            fields[i].setAccessible(true);


          /*  Field modifiersField = null;
            modifiersField = Field.class.getDeclaredField("modifiers");*/
             /*   modifiersField.setAccessible(true);

                modifiersField.setInt(fields[i], fields[i].getModifiers() & ~Modifier.FINAL);
*/


            if((fields[i].getModifiers() & 8) == 8){
               // System.out.println(fields[i].getName()+" type:"+fields[i].getType()+" "+fields[i].getModifiers()+" value:"+fields[i].get(null));
                String name = fields[i].getName();
                String value = properties.getProperty(name);
        if(value!=null){
            Class type = fields[i].getType();

            if(type==String.class){
                fields[i].set(null,value);
            }else if(type==Integer.class){
                fields[i].set(null,Integer.valueOf(value));
            }
            else if(type==Double.class){
                fields[i].set(null,Double.valueOf(value));
            }else if(type==Float.class){
                fields[i].set(null,Float.valueOf(value));
            }
            else if(type==int.class){
                fields[i].set(null,Integer.valueOf(value));
            }
        }
                //System.out.println(fields[i].getName());
                //System.out.println(fields[i].get(null));
                //fields[i].set(null,1);

            }
        }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /*
    modifer
    1  public
    2  private
    4  protected
    16 final
    8  static*/

    public static void main(String[] args) {
        try {
            Properties props = new Properties();

            InputStream in = ClassLoader.getSystemResourceAsStream("properties/config.properties");
            PathManager.getInstance().getHomePath().resolve("src/main/resource/config.properties").toFile();
            StaticUtil.loadTo(props,Config.class);
            System.out.println(Config.a);



        } catch (Exception e) {
            e.printStackTrace();
        }

        //  System.out.println(Config.a);

        System.out.println("所有任务执行完毕");
    }
}