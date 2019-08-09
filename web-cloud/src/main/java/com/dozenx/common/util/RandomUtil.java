package com.dozenx.common.util;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
public class RandomUtil {

    public static int getRandom(int num){
        return (int)(Math.random()*Math.pow(10,num));
    }
}
