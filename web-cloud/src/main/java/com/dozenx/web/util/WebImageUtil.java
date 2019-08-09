package com.dozenx.web.util;

import com.dozenx.common.exception.ValidException;
import com.dozenx.common.util.*;
import com.dozenx.common.util.encrypt.Base64Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:26 2018/6/11
 * @Modified By:
 */
public class WebImageUtil {
    private static final Logger logger  = LoggerFactory.getLogger(WebImageUtil.class);
    public static String  saveUploadFileToDisk(MultipartFile file,String path,String name ){
        Path pth = Paths.get(path);
        File destFile = pth.toFile();
        if(!destFile.exists()){
            destFile.mkdirs();
        }
        String type = file.getContentType();
        try {
            FileUtil.writeFileFromStream(file.getInputStream(),name, pth);
        } catch (Exception var10) {
            throw  new ValidException("E2030004","图片大小不合法");
        }
        return FilePathUtil.joinPath(path,name);
    }

    /**
     * 保存spring mvc 的 MultipartFile对象到路径上
     * @param file
     * @param path
     * @param name
     * @return
     */
    public static String  saveUploadImageToDisk(MultipartFile file,String path,String name ) throws Exception {
        Path pth = Paths.get(path);
        File destFile = pth.toFile();
        if(!destFile.exists()){
            destFile.mkdirs();
        }
        destFile= pth.resolve(name).toFile();
        logger.info("path"+path);
        logger.info("name"+name);
        String type = file.getContentType();
        if(StringUtil.isBlank(type)) {
            throw  new ValidException("E2030001","图片格式为空不正确");
        } else if(!type.equals("image/jpeg") && !type.equals("image/png") && !type.equals("image/bmp")) {

            throw  new ValidException("E2030002","图片格式不正确");
        } else {


//            BufferedImage img = null;
            try {
                InputStream inputStream = file.getInputStream();
                type = type.substring(6).toLowerCase();
                logger.debug("上传图片格式"+type);
                 ImageUtil.saveAsJpg(inputStream,Paths.get(path),name);
            } catch (Exception e) {
                logger.info("保存图片出错",e);
                throw  new ValidException("E2030004","图片读取出错");
            }
//            if(img == null || img.getWidth() <= 0 || img.getHeight() <= 0) {
//                throw  new ValidException("E2030003","图片大小不合法");
//            }
//            try {
//                ImageIO.write(img, Config.getInstance().getImage().getType(), destFile);
//            } catch (Exception e) {
//                logger.info("保存图片出错",e);
//                throw  new ValidException("E2030005","图片保存出错");
//            }
            return FilePathUtil.joinPath(path,name);
        }
    }


    /**
     * Image的Unique的唯一判断
     * @param image      //文件
     * @param imageSize   //文件大小
     * @param imgOrgnalName //文件名
     * @return
     * @throws Exception
     * @author: 王作品
     * @date: 2017/9/26 0026 下午 14:24
     */

    public static String  getImageMD5(MultipartFile image, Long imageSize, String imgOrgnalName )throws  Exception {
        if(imageSize==0){
            imageSize=image.getSize();
        }
        InputStream in =  image.getInputStream();
        byte[] buf = new byte[1024];
        int len=0;
        try {
            len =in.read(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            in.close();
        }
        return  MD5Util.getStringMD5String(new String(buf,0,len)+imageSize+imgOrgnalName+"Awifi");
    }
    public static void main(String[] args){
        String base64 = ImageUtil.ImageToBase64ByLocal("G:\\workspace\\dozenx\\ui\\src\\main\\webapp\\upload\\2018\\11\\20\\1542706015319.png");
        byte[] bytes = Base64Decoder.decodeToBytes(base64);
        InputStream sbs = new ByteArrayInputStream(bytes);
        try {
            BufferedImage bufferedImage = ImageIO.read(sbs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
