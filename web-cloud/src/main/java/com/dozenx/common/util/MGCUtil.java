package com.dozenx.common.util;

import com.dozenx.common.Path.PathManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 敏感词
 * Created by dozen.zhang on 2016/5/11.
 */
public class MGCUtil {
    public static boolean contain(String s){
        File file = PathManager.getInstance().getClassPath().resolve("properties/baidumgc.properties").toFile();
        FileReader fr=null;
        BufferedReader reader=null;
        try {
            //FileInputStream fis = new FileInputStream(file);
            fr =new FileReader(file);
            reader =new BufferedReader(fr);

            String line=null;
            while ((line=reader.readLine())!=null){
                System.out.println(line);
                if(!line.equals("") && s.indexOf(line)!=-1){
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader!=null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(fr!=null)
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }
    public static void main(String args[]){
        MGCUtil.contain("收购");
    }
}
