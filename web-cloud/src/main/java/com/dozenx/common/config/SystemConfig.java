
package com.dozenx.common.config;

import com.dozenx.common.config.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author dozen.zhang
 *
 */
public class SystemConfig {
     private static final int i=1;
    private String temp="";

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public static void main(String args[]){
        Field[] fields= com.dozenx.common.config.Config.class.getDeclaredFields();
        for(Field field:fields){
           String descriptor=Modifier.toString(field.getModifiers());//获得其属性的修饰
           descriptor=descriptor.equals("")==true?"":descriptor+" ";
           try {
               //field.set(new             SystemConfig(), 2);
             // if(descriptor.indexOf("private")>=0)continue;
               System.out.println(descriptor+field.getName()+"="+field.get(new Config()));
            
           } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      
         }
    }
}
