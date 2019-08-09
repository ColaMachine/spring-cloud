package com.dozenx.common.util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.imageio.ImageIO;
//import com.drew.imaging.ImageMetadataReader;
//import com.drew.imaging.ImageProcessingException;
//import com.drew.metadata.Directory;
//import com.drew.metadata.Metadata;
//import com.drew.metadata.exif.ExifImageDirectory;

public class ExifTurnImgUtil {

    /**
     * 获取图片信息，并判断是否要做旋转操作
     * （ios图片可能会发生旋转现象）
     * @param
     */
//    public static BufferedImage getExifAndTurnByFile(File file){
//        BufferedImage result = null;
//        try {
//            Metadata metadata = ImageMetadataReader.readMetadata(file);
//            Iterable<Directory> directories = metadata.getDirectoriesOfType(Directory.class);
//            Stream<Directory> directoryStream= StreamSupport.stream(directories.spliterator(), false);
//            int angel = 0;
//            Optional<Directory> exif = directoryStream.filter(x -> x.containsTag(ExifImageDirectory.TAG_ORIENTATION)).findAny();
//            if(exif.isPresent())
//                angel = getTurnrank(exif.get().getDescription(ExifImageDirectory.TAG_ORIENTATION));
//
//            result = ImageIO.read(file);
//            // 旋转
//            if(angel != 0){
//                result = turnByDegree(result, angel);
//            }
//        } catch (ImageProcessingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    public static int getTurnrank(String description) {
        int orientation = 0;
        if ("Bottom, right side (Rotate 180)".equalsIgnoreCase(description)) {
            orientation = 180;
        } else if ("Right side, top (Rotate 90 CW)".equalsIgnoreCase(description)) {
            orientation = 90;
        } else if ("Left side, bottom (Rotate 270 CW)".equalsIgnoreCase(description)) {
            orientation = 270;
        }
        return  orientation;
    }

    /**
     * 图片旋转
     * @param degree 旋转角度
     */
    private static BufferedImage turnByDegree(BufferedImage bi, int degree) {
        BufferedImage result = null;
        try {
            int swidth = 0; // 旋转后的宽度
            int sheight = 0; // 旋转后的高度
            int x; // 原点横坐标
            int y; // 原点纵坐标

            //调整角度
            degree = degree % 360;
            if (degree < 0) {
                degree = 360 + degree;
            }
            double theta = Math.toRadians(degree);

            // 确定旋转后的宽和高
            if (degree == 180 || degree == 0 || degree == 360) {
                swidth = bi.getWidth();
                sheight = bi.getHeight();
            } else if (degree == 90 || degree == 270) {
                sheight = bi.getWidth();
                swidth = bi.getHeight();
            } else {
                swidth = (int) (Math.sqrt(bi.getWidth() * bi.getWidth() + bi.getHeight() * bi.getHeight()));
                sheight = (int) (Math.sqrt(bi.getWidth() * bi.getWidth() + bi.getHeight() * bi.getHeight()));
            }

            x = (swidth / 2) - (bi.getWidth() / 2);
            y = (sheight / 2) - (bi.getHeight() / 2);

            result = new BufferedImage(swidth, sheight, bi.getType());

            Graphics2D gs = (Graphics2D) result.getGraphics();
            gs.setColor(Color.white);
            gs.fillRect(0, 0, swidth, sheight);

            AffineTransform at = new AffineTransform();
            at.rotate(theta, swidth / 2, sheight / 2);
            at.translate(x, y);
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            result = op.filter(bi, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}

