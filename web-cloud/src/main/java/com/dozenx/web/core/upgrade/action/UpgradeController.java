package com.dozenx.web.core.upgrade.action;

import com.dozenx.swagger.annotation.APIs;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.CmdUtil;
import com.dozenx.common.util.FileUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 9:14 2018/7/4
 * @Modified By:
 */

@APIs(description = "升级模块")
@Controller
@RequestMapping(Constants.WEBROOT + "/sys/upgrade")
public class UpgradeController extends BaseController {


    /**
     * 日志
     **/
    private static final Logger logger = LoggerFactory.getLogger(UpgradeController.class);

    public static void main(String args[]) {
        String pkgUrl = "http://alpha-ezhike.51awifi.com/ezhikesrv/tmp/a.zip";
        try {
            System.out.println(PathManager.getInstance().getHomePath());
            HttpRequestUtil.saveFileFromUrl(pkgUrl, "a.zip", PathManager.getInstance().getHomePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = PathManager.getInstance().getHomePath() .resolve( "a.zip").toFile();
        if (file.exists()) {
            System.out.println("hello");

            byte[] bts = FileUtil.getBytes(file);
            String md5 = DigestUtils.md5Hex(bts);
            String checkMd5 = md5 + bts.length;
            String version="a";
            System.out.println("beta-advert.51awifi.com/advertsrv/sys/upgrade/upload?version=a&pkgUrl=" + URLEncoder.encode(pkgUrl) + "&md5=" + checkMd5);
        } else {
            System.out.println("file not exist");
        }
    }

    /**
     * 说明: 升级服务
     *
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO upgrade(HttpServletRequest request) {
        //一个升级程序 会连接到主站上面
        String version = request.getParameter("version");
        //会返回1个json数据
        String pkgUrl = request.getParameter("pkgUrl");

        String restart = request.getParameter("restart");
        String checkMd5 = request.getParameter("md5");
        //格式如下
        /*
        {
            version:1,
            pkgPath:http://xxxx.1123123.zip,
            date:'2018-07-04 09:22:44',

        }
         */
        //比对当前包版本和传的值 如果小于等于本地版本 不做升级
        //将包下载到本地
        try {
            HttpRequestUtil.saveFileFromUrl(pkgUrl, version, PathManager.getInstance().getHomePath());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getResult(1, e.getMessage());
        }
        //判断文件是否存在
        File zipFile = PathManager.getInstance().getHomePath().resolve(version).toFile();
        if (!zipFile.exists()) {
            logger.error("上传的zipfile文件不存在");
            return this.getResult(1, "上传的zipfile 文件不存在");
        }
        //将包解压到目录下 //覆盖文件
        byte[] bts = FileUtil.getBytes(zipFile);
        String md5 = DigestUtils.md5Hex(bts);
        if (!(md5 + bts.length).equals(checkMd5)) {
            return this.getResult();
        }
        try {
            FileUtil.decompressZip(zipFile, PathManager.getInstance().getHomePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
            return this.getResult(2, e.getMessage());
        }
        //删除文件
        logger.info("可以删除文件");
        zipFile.delete();
        //重启服务
        if (StringUtil.isNotBlank(restart)) {
//            logger.info("sd");
            //添加定时任务 重启tomcat 服务
            try {
                String result = new CmdUtil().execCommand("../bin/shutdown.sh", PathManager.getInstance().getHomePath().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            logger.info("st");
//            new CmdUtil().execCommand("../bin/startup.sh", PathManager.getInstance().getHomePath().toString());
        }
        return this.getResult();
    }

    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO exec(HttpServletRequest request) {
        //一个升级程序 会连接到主站上面
        String cmd = request.getParameter("cmd");
        String path = request.getParameter("path");
        // http://127.0.0.1:7561/advertsrv/sys/upgrade/exec?path=/service/tomcat-npbiz-advert-7561/npbiz-advert&cmd=mv%20telnet%20META-INF/
        //重启服务
        if (StringUtil.isNotBlank(cmd)) {
            //添加定时任务 重启tomcat 服务
            try {
                String s = new CmdUtil().execCommand(cmd, PathManager.getInstance().getHomePath().resolve(path).toString());
                return this.getResult(s);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return this.getResult();
    }




}
