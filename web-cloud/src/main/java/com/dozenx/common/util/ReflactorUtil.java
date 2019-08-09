package com.dozenx.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by dozen.zhang on 2016/4/5.
 */
public class ReflactorUtil {
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

    public static void setValue(Object obj,Object val,String name) throws NoSuchFieldException, IllegalAccessException {
        Class  clz = obj.getClass();
        Field field = clz.getDeclaredField(name);
        field.set(obj,val);

    }
}
