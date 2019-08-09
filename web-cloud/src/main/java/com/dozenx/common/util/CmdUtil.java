/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明:
 */
package com.dozenx.common.util;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CmdUtil.class);
    public static String execCommand(String commandStr,String filePath)throws IOException{
        try {
            logger.info("在"+filePath+"下执行命令"+commandStr);
            File dir = new File(filePath);
            // String command="netstat -an";
            String os = System.getProperty("os.name");
            String[] cmdA = { "/bin/sh", "-c", commandStr };
            String command = " "+commandStr;
            if(os.toLowerCase().startsWith("win")){//如果是windows 操作系统
                command = "cmd /c "+commandStr;
                cmdA = new String[]{command};
            }


            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmdA, null, dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            StringBuffer sb = new StringBuffer();
            String inline;
            while (null != (inline = br.readLine())) {
                sb.append(inline).append("\n");
            }
            System.out.println(sb.toString());
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }


    }
    public String execCommand(String[] arstringCommand)throws IOException {

        logger.info("执行命令"+arstringCommand[0]);
        for (int i = 0; i < arstringCommand.length; i++) {
            System.out.print(arstringCommand[i] + " ");
        }
        try {

            Runtime r = Runtime.getRuntime();
            Process p = r.exec(arstringCommand);
            BufferedReader br = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            StringBuffer sb = new StringBuffer();
            String inline;
            while (null != (inline = br.readLine())) {
                sb.append(inline).append("\n");
            }
            System.out.println(sb.toString());
            br.close();
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }
    public String execCommand(String arstringCommand) throws IOException {
        logger.info("执行命令"+arstringCommand);
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(arstringCommand);
            BufferedReader br = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            StringBuffer sb = new StringBuffer();
            String inline;
            while (null != (inline = br.readLine())) {
                sb.append(inline).append("\n");
            }
            System.out.println(sb.toString());
            br.close();
            return sb.toString();

       } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
       }
    }

    public void cmd(){
        //打开记算器

    }

    public static void main(String[] args){
        String[] arstringCommand = new String[] {
                "cmd ",
                "/k",
                "cd",
                "c:/zzw/calendar/gulp;",
                "gulp",
                "build",
        };
        try {
            new CmdUtil(). execCommand(arstringCommand);
            String cmd = "cmd /k start notepad";
            new CmdUtil().execCommand(cmd);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //打开记事本

    }
}
