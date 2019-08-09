package com.dozenx.web.core.log;

/**
 * Created by luying on 16/7/24.
 */
public class LogUtil {
    public static void println1(String s){


        Throwable e =new Throwable();
        StackTraceElement[] eles = e.getStackTrace();
        System.out.println(System.currentTimeMillis()+""+eles[1]+":"+s);
    }

    public static void pringln(String s){

        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        String className = ste.getClassName();
        String methodName = ste.getMethodName();
        int lineNumber = ste.getLineNumber();
        String ret =  className+"." +methodName+"()."+lineNumber+" says:"+s;
        System.out.println(ret);
    }

    public static void println(String s, Object ... objectes){
        String b= String.format(s,objectes);
        Throwable e =new Throwable();
        StackTraceElement[] eles = e.getStackTrace();
        System.out.println(System.currentTimeMillis()+""+eles[1]+":"+b);
    }
    public static void err(String s){
        Throwable e =new Throwable();
        StackTraceElement[] eles = e.getStackTrace();
        System.out.println(System.currentTimeMillis()+""+eles[1]+":"+s);
        System.exit(0);
    }
    public static void err(String s,Object[] objectes){
        String b= String.format(s,objectes);
        println(b);
        System.exit(0);
    }

    public static void err(Exception  e){
       // println(s);
        e.printStackTrace();
        System.exit(0);
    }

}
