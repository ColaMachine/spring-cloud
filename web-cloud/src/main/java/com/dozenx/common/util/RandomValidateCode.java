package com.dozenx.common.util;

import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.Config;
import com.dozenx.common.config.SysConfig;
import com.dozenx.common.util.StringUtil;
import com.dozenx.common.util.UUIDUtil;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class RandomValidateCode {


    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";//放到session中的key
    private Random random = new Random();
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
    
    private int width = 80;//图片宽
    private int height = 26;//图片高
    private int lineSize = 5;//干扰线数量
    private int stringNum = 4;//随机产生字符数量
    private int rotate_value=5;//摇摆幅度
    /*
     * 获得字体
     */
    private Font getFont(){
        return new Font("Fixedsys",Font.CENTER_BASELINE,18);
    }
    /*
     * 获得颜色
     */
    private Color getRandColor(int fc,int bc){
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }
    /**
     * 生成随机图片
     */
    /*public void getImgRandcode(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(110, 133));
        //绘制干扰线
        for(int i=0;i<=lineSize;i++){
            drawLine(g);
        }
        //绘制随机字符
        String randomString = "";
        for(int i=1;i<=stringNum;i++){
            //randomString=drowString(g,randomString,i);
        }
        System.out.println(randomString);
        g.dispose();
        try {
        	File file =new File("D:/a.jpg");
            ImageIO.write(image, "JPEG", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    
    
    /**
     * 生成随机图片
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public String[] getImgRandcode(int length,String filename) throws FileNotFoundException, IOException {
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D  g = (Graphics2D )image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(110, 133));
        //绘制干扰线
        for(int i=0;i<=lineSize;i++){
            drawLine(g);
        }
        //绘制随机字符
        String randomString = "";
        for(int i=1;i<=length;i++){
            randomString=drawString(g,randomString,i);
        }
        //System.out.println(randomString);
        g.dispose();
       if(com.dozenx.common.util.StringUtil.isBlank(filename)){
           filename = com.dozenx.common.util.UUIDUtil.getUUID()+".jpg";
       }
       
       File file= PathManager.getInstance().getVcodePath().resolve(filename+".jpg").toFile();
 /*       File folder =new File(SysConfig.REALPATH+File.separator+SysConfig.VALIDATECODE_IMG_FOLDER);
            File file =new File(SysConfig.REALPATH+File.separator+SysConfig.VALIDATECODE_IMG_FOLDER+"/"+filename+".jpg");
            //File file =new File("g:/vc/"+filename+".jpg");*/
             if(file.exists()){
                 System.out.println("文件已经存在");
            }else{
              //如果要创建的多级目录不存在才需要创建。
                file.createNewFile();
               // file.mkdirs();
             }
             ByteArrayOutputStream  baos = new ByteArrayOutputStream();
             ImageIO.write(image, "JPEG",baos);//将内存中的图片通过流动形式输出到客户端
             byte[] bytes = baos.toByteArray();  
              BASE64Encoder encoder = new BASE64Encoder();
            String  result = encoder.encodeBuffer(bytes).trim();
            ImageIO.write(image, "JPEG",file);//将内存中的图片通过流动形式输出到客户端
        return new String[]{Config.getInstance().getImage().getVcodeDir()+"/"+filename+".jpg",randomString,result};
    }
    /**
     * 生成随机图片
     * @throws IOException
     * @throws FileNotFoundException
     */
    public String[] getImgRandcodeBuffer(int length) throws FileNotFoundException, IOException {
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D  g = (Graphics2D )image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(110, 133));
        //绘制干扰线
        for(int i=0;i<=lineSize;i++){
            drawLine(g);
        }
        //绘制随机字符
        String randomString = "";
        for(int i=1;i<=length;i++){
            randomString=drawString(g,randomString,i);
        }
        //System.out.println(randomString);
        g.dispose();

        String filename = com.dozenx.common.util.UUIDUtil.getUUID()+".jpg";
 /*       File folder =new File(SysConfig.REALPATH+File.separator+SysConfig.VALIDATECODE_IMG_FOLDER);
            File file =new File(SysConfig.REALPATH+File.separator+SysConfig.VALIDATECODE_IMG_FOLDER+"/"+filename+".jpg");
            //File file =new File("g:/vc/"+filename+".jpg");
             if(folder.exists()){
                 System.out.println("多级目录已经存在不需要创建！！");
            }else{
              //如果要创建的多级目录不存在才需要创建。
             //   folder.createNewFile();
               folder.mkdirs();
             }*/
        ImageIO.write(image, "JPEG",PathManager.getInstance().getVcodePath().resolve(filename).toFile());//将内存中的图片通过流动形式输出到客户端
        return new String[]{SysConfig.VALIDATECODE_IMG_FOLDER+"/"+filename+".jpg",randomString};
    }
    /**
     * 生成随机图片
     * @throws IOException
     * @throws FileNotFoundException
     */
    public String[] getImgRandcode() throws FileNotFoundException, IOException {
        return this.getImgRandcode(stringNum,"");
    }


    /*
     * 绘制字符串
     */
    private String drawString(Graphics2D  g,String randomString,int i){
        //javax.servlet.ServletOutputStream o;
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
       int rotateAngle= random.nextInt(rotate_value*2)-rotate_value;
        //g.translate(random.nextInt(3), random.nextInt(3));
       int _x=random.nextInt(3);
       int _y=random.nextInt(3);
        g.translate(13*i+_x, 16+_y);
       g.rotate(rotateAngle * Math.PI / 180);
        g.drawString(rand,0, 0);
        g.rotate(-rotateAngle * Math.PI / 180);
        g.translate(-13*i-_x, -16-_y);
       // g.drawString(rand,13*i, 16);
        return randomString;
    }
    /*
     * 绘制字符串
     */
    private void drawString(Graphics2D  g,char car,int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        int rotateAngle= random.nextInt(rotate_value*2)-rotate_value;
        //g.translate(random.nextInt(3), random.nextInt(3));
        int _x=random.nextInt(3);
        int _y=random.nextInt(3);
        g.translate(13*i+_x, 16+_y);
        g.rotate(rotateAngle * Math.PI / 180);
        g.drawString(car+"",0, 0);
        g.rotate(-rotateAngle * Math.PI / 180);
        g.translate(-13*i-_x, -16-_y);
        // g.drawString(rand,13*i, 16);

    }
    /*
     * 绘制干扰线
     */
    private void drawLine(Graphics g){
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x+xl, y+yl);
    }
    /*
     * 获取随机的字符
     */
    public String getRandomString(int num){
        return String.valueOf(randString.charAt(num));
    }
    public static void main(String args[]){
    	RandomValidateCode r=new RandomValidateCode();
    	try {
            r.getImgRandcode();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 生成随机图片
     * @throws IOException
     * @throws FileNotFoundException
     */
    public String[] getImgRandcode(String str,String filename) throws FileNotFoundException, IOException {
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D  g = (Graphics2D )image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(110, 133));
        //绘制干扰线
        for(int i=0;i<=lineSize;i++){
            drawLine(g);
        }
        //绘制随机字符
        for(int i=1;i<=str.length();i++){
            drawString(g,str.charAt(i-1),i);
        }
        //System.out.println(randomString);
        g.dispose();
        if(StringUtil.isBlank(filename)){
            filename = UUIDUtil.getUUID()+".jpg";
        }

        File file=PathManager.getInstance().getVcodePath().resolve(filename+".jpg").toFile();
        if(file.exists()){
            System.out.println("文件已经存在");
        }else{
            //如果要创建的多级目录不存在才需要创建。
            file.createNewFile();
        }
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG",baos);//将内存中的图片通过流动形式输出到客户端
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String  result = encoder.encodeBuffer(bytes).trim();
        try {
            ImageIO.write(image, "JPEG", file);//将内存中的图片通过流动形式输出到客户端
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return new String[]{Config.getInstance().getImage().getVcodeDir()+"/"+filename+".jpg",str,result};
    }
}
