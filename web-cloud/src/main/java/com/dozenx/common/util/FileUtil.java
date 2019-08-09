/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明:
 */
package com.dozenx.common.util;


//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.SCPClient;
//import ch.ethz.ssh2.SFTPv3Client;
//import ch.ethz.ssh2.StreamGobbler;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.CmdUtil;
import com.dozenx.common.util.FileType;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public class FileUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FileUtil.class);

    public static List<File> readAllFileInFold(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if (!file.exists()) {
            throw new IOException("path file not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("path file not exist");
        }
        return listFile(file);
    }
    public static List<File> listOneLevelFile(File file) {
        List<File> fileList = new ArrayList<>();
        File[] fileAry = file.listFiles();
        if(fileAry==null){
            return null;
        }
        for (File childFile : fileAry) {
            if (childFile.isDirectory()) {
                continue;
            } else {
                fileList.add(childFile);
            }
        }
        return fileList;
    }
    public static List<File> listFile(File file) {
        List<File> fileList = new ArrayList<>();
        File[] fileAry = file.listFiles();
        if(fileAry==null){
            return null;
        }
        for (File childFile : fileAry) {
            if (childFile.isDirectory()) {
                fileList.addAll(listFile(childFile));
            } else {
                fileList.add(childFile);
            }
        }
        return fileList;
    }


    public static List<File> listFile(File file,boolean recursion) {
        List<File> fileList = new ArrayList<>();
        File[] fileAry = file.listFiles();
        for (File childFile : fileAry) {
            if (recursion && childFile.isDirectory()) {
                fileList.addAll(listFile(childFile));
            } else {
                fileList.add(childFile);
            }
        }
        return fileList;
    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        String content = "";
        try{
            content =  readFile2Str(file);
        }catch(Exception e ){
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 读取文件内容 忽略行注释
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFile2Str(File file) throws IOException {
        if (!file.exists()) {
            //throw new IOException("path file "+file.getPath()+" not exist");
            return "";
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            //过滤掉注释内容
            s = s.trim();
            if (s.startsWith("//")) {
                continue;
            }
            templateStr.append(s + "\r\n");
        }
        if (templateStr == null || templateStr.toString().length() == 0) {
            throw new IOException("file is empty: " + file);
        }
        return templateStr.toString();
    }


    /**
     * 写文件
     *
     * @param filePath
     * @param content
     * @throws IOException
     */
    public static void writeFile(String filePath, String content) throws IOException {
        FileWriter fileWritter = null;
        File file = new File(filePath);
        // BufferedWriter bufferWritter=null;
        try {
            // if file doesnt exists, then create it
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            fileWritter = new FileWriter(file, false);
            fileWritter.write(content);
            // bufferWritter = new BufferedWriter(fileWritter);
            // bufferWritter.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            System.out.println(file.getAbsolutePath().toString());
            e.printStackTrace();
            throw e;
        } finally {
            /*
			 * bufferWritter.flush(); if(bufferWritter!=null )
			 * bufferWritter.close();
			 */

            fileWritter.flush();
            if (fileWritter != null)
                fileWritter.close();

        }
    }

    /**
     * 写文件
     *
     * @param file
     * @param content
     * @throws IOException
     */
    public static void writeFile(File file, String content) throws IOException {
        try {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            FileWriter fileWritter = new FileWriter(file, false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void writeFile(File file, byte[] data) throws IOException {
        try {
            // if file doesnt exists, then create it
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            FileOutputStream out = new FileOutputStream(file, false);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
            bufferedOutputStream.write(data);
            bufferedOutputStream.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String args[]) {
        try {
            FileReader fr = new FileReader("G://V2.2.7.txt");
            BufferedReader br = new BufferedReader(fr);
            String s;
            String name = "";
            StringBuffer result = new StringBuffer();
            while ((s = br.readLine()) != null) {
                // System.out.println(s);
                // System.out.println(s.split("\\s+").length);
                String arr[] = s.split("\\s+");
                System.out.println(arr[0]);
                if (name.equals("")) {
                    name = arr[arr.length - 1];
                } else if (arr[arr.length - 1].trim().equals(name.trim())) {

                    result.append("'").append(arr[0]).append("',");

                } else {

                    // System.out.println(name+"select distinct IPAddress from
                    // wii_device_ssid where deviceid in(select id from
                    // wii_device where DevId in ("+result.toString()+"))");
                    result = new StringBuffer();
                    name = arr[arr.length - 1];
                }
                if (arr.length == 3) {

                }

            }
            // System.out.println(name+"select distinct IPAddress from
            // wii_device_ssid where id in(select id from wii_device where DevId
            // in ("+result.toString()+")");
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFile2List(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if (!file.exists()) {
            throw new IOException("read file failed path" + path);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        // BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList();
        String s;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }

    public static List<String> readFile2List(File file) throws IOException {

        if (!file.exists()) {
            throw new IOException("read file failed path");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        // BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList();
        String s;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }


    /**
     * 上传文件 uploadfile
     *
//     * @param localRootPath  c:/forder
//     * @param remoteRootPath /folder
//     * @param relativePaths  a.txt
//     * @param userName       username
//     * @param pwd            password
//     * @param serverIp       192.168.11.231
     */
//    public static void upload(String localRootPath, String remoteRootPath, String[] relativePaths, String userName,
//                              String pwd, String serverIp) {
//        Connection con = new Connection(serverIp);
//
//        try {
//            con.connect();
//            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
//            if (!isAuthed) {
//                logger.error("ssh upload file username & pwd authed failed");
//                return;
//            }
//
//            SCPClient scpClient = con.createSCPClient(); //
//            SFTPv3Client sftpClient = new SFTPv3Client(con);
//            for (int i = 0; i < relativePaths.length; i++) {
//                String relativePath = relativePaths[i];
//                if (relativePath.startsWith(File.separator)) {
//                    relativePath = relativePath.substring(1);
//                }
//                String localPath = localRootPath + File.separator + relativePath;
//                String remotePath = remoteRootPath + File.separator + relativePath;
//                int index = remotePath.lastIndexOf(File.separator);
//                String remoteFileDir = "";
//                if (index != -1) {
//                    ch.ethz.ssh2.Session session = con.openSession();
//                    remoteFileDir = remotePath.substring(0, index);
//
//                    session.execCommand("mkdir -p " + remoteFileDir); //
//                    logger.debug("Here is some information about the remote host:");
//                    InputStream stdout = null;
//                    BufferedReader br = null;
//                    try {
//                        stdout = new StreamGobbler(session.getStdout());
//                        br = new BufferedReader(new InputStreamReader(stdout));
//                    } catch (Exception e) {
//                        logger.error("error in print ssh mkdir log", e);
//                    } finally {
//                        if (br != null) {
//                            br.close();
//                        }
//                        if (stdout != null) {
//                            stdout.close();
//                        }
//                    }
//                    while (true) {
//                        String line = br.readLine();
//                        if (line == null)
//                            break;
//                        logger.debug(line);
//                    }
//                    // Show exit status,if available(otherwise"null")
//                    logger.debug("ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir,
//                    // 6);
//                    logger.info("创建目录+" + remoteFileDir);
//                    session.close();
//                    logger.debug("local image file :" + localPath);
//                } else {
//                    logger.debug("error:in copy " + localPath);
//                }
//                scpClient.put(localPath, remoteFileDir); // 从本地复制文件到远程目录
//            }
//
//        } catch (IOException e) {
//            logger.error("ssh upload file failed ", e);
//
//        } finally {
//            con.close();
//        }
//
//    }

    private static boolean saveImageToDisk(byte[] data, String path, String imageName) throws IOException {
        int len = data.length;

        File file =new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 写入到文件
   /*     System.out.println(data);
        for(int i=0;i<data.length;i++){
            System.out.print(data[i]);
        }*/
        FileOutputStream outputStream = new FileOutputStream(new File(path, imageName));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        return true;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static  byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static  byte[] getBytes(File file){
        byte[] buffer = null;
        try {

            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void getFile(byte[] bfile, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeFileFromStream(InputStream in, String fileName, Path path) {
        OutputStream out =null;
        try{
            File file = path.resolve(fileName).toFile();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            byte[] bts = new byte[1024];

            out = new FileOutputStream(file);
            int len = 0;
            while ((len = in.read(bts, 0, 1024)) > 0) {
                out.write(bts, 0, len);
            }
        }catch(Exception e ){

        }  finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }



    //缓存文件头信息-文件头信息
    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    static {
// images
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");
        mFileTypes.put("424D", "bmp");
//
        mFileTypes.put("41433130", "dwg"); // CAD
        mFileTypes.put("38425053", "psd");
        mFileTypes.put("7B5C727466", "rtf"); // 日记本
        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("68746D6C3E", "html");
        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
        mFileTypes.put("D0CF11E0", "doc");
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("252150532D41646F6265", "ps");
        mFileTypes.put("255044462D312E", "pdf");
        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("1F8B08", "gz");
        mFileTypes.put("", "");
        mFileTypes.put("", "");
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    public static String getFileType(String filePath) {
        return mFileTypes.get(getFileHeader(filePath));
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }

    /**
     * 根据文对象获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(File filePath) {
        InputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }


    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src
     *            要读取文件头信息的文件的byte数组
     * @return 文件头信息
     *下面这段代码就是用来对文件类型作验证的方法，
    第一个参数是文件的字节数组，第二个就是定义的可通过类型。代码很简单，         主要是注意中间的一处，将字节数组的前四位转换成16进制字符串，并且转换的时候，要先和0xFF做一次与运算。这是因为，整个文件流的字节数组中，有很多是负数，进行了与运算后，可以将前面的符号位都去掉，这样转换成的16进制字符串最多保留两位，如果是正数又小于10，那么转换后只有一位，需要在前面补0，这样做的目的是方便比较，取完前四位这个循环就可以终止了。
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }



    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        //新建目标目录
        (new File(targetDir)).mkdirs();
        //获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                //源文件
                File sourceFile = file[i];
                //目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                //准备复制的源文件夹
                String dir1 = sourceDir + file[i].getName();
                //准备复制的目标文件夹
                String dir2 = targetDir + File.separator + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }

    }

    public static void copyFile(File sourcefile, File targetFile) throws IOException {

        //新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourcefile);
        BufferedInputStream inbuff = new BufferedInputStream(input);

        //新建文件输出流并对它进行缓冲
        FileOutputStream out = new FileOutputStream(targetFile);
        BufferedOutputStream outbuff = new BufferedOutputStream(out);

        //缓冲数组
        byte[] b = new byte[1024 * 5];
        int len = 0;
        while ((len = inbuff.read(b)) != -1) {
            outbuff.write(b, 0, len);
        }

        //刷新此缓冲的输出流
        outbuff.flush();

        //关闭流
        inbuff.close();
        outbuff.close();
        out.close();
        input.close();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 压缩zip格式文件
     *
     * @param targetFile  输出的文件。
     * @param sourceFiles 带压缩的文件数组。
     * @return 如果所有文件压缩成功，则返回true；如果有任何文件未成功压缩，则返回false。
     * @throws IOException 如果出错后无法删除目标文件或无法覆盖目标文件。
     */
    public static boolean compressZip(File targetFile, File... sourceFiles) throws IOException {
        ZipOutputStream zipOut;
        boolean flag;
        if (targetFile.exists() && !targetFile.delete()) {
            throw new IOException();
        }
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(targetFile));
            BufferedOutputStream out = new BufferedOutputStream(zipOut);
            flag = compressZip(zipOut, out, "", sourceFiles);
            out.close();
            zipOut.close();
        } catch (IOException e) {
            targetFile.delete();
            throw new IOException(e);
        }
        return flag;
    }

    private static boolean compressZip(ZipOutputStream zipOut, BufferedOutputStream out, String filePath, File... sourceFiles)
            throws IOException {
        if (null != filePath && !"".equals(filePath)) {
            filePath += filePath.endsWith(File.separator) ? "" : File.separator;
        } else {
            filePath = "";
        }
        boolean flag = true;
        for (File file : sourceFiles) {
            if (null == file) {
                continue;
            }
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                if (null == fileList) {
                    return false;
                } else if (1 > fileList.length) {
                    zipOut.putNextEntry(new ZipEntry(filePath + file.getName() + File.separator));
                } else {
                    flag = compressZip(zipOut, out, filePath + File.separator + file.getName(), fileList) && flag; // 只要flag有一次为false，整个递归的结果都为false。
                }
            } else {
                zipOut.putNextEntry(new ZipEntry(filePath + file.getName()));
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                int bytesRead;
                while (-1 != (bytesRead = in.read())) {
                    out.write(bytesRead);
                }
                in.close();
            }
            out.flush();
        }
        return flag;
    }

    /**
     * 解压zip格式文件
     *
     * @param originFile zip文件。
     * @param targetDir  要解压到的目标路径。
     * @return 如果目标文件不是zip文件则返回false。
     * @throws IOException 如果发生I/O错误。
     */
    public static boolean decompressZip(File originFile, String targetDir) throws IOException {
        if (com.dozenx.common.util.FileType.ZIP != getFileType(originFile)) {
            return false;
        }
        if (!targetDir.endsWith(File.separator)) {
            targetDir += File.separator;
        }
        ZipFile zipFile = new ZipFile(originFile);
        ZipEntry zipEntry;
        Enumeration<ZipEntry> entry = zipFile.getEntries();
        while (entry.hasMoreElements()) {
            zipEntry = entry.nextElement();
            String fileName = zipEntry.getName();
            File outputFile = new File(targetDir + fileName);
            if (zipEntry.isDirectory()) {
                forceMkdirs(outputFile);
                continue;
            } else if (!outputFile.getParentFile().exists()) {
                forceMkdirs(outputFile.getParent());
            }
            OutputStream outputStream = new FileOutputStream(outputFile);
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            int len;
            byte[] buffer = new byte[8192];
            while (-1 != (len = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();
        }
        zipFile.close();
        return true;
    }

    /**
     * 获取文件真实类型
     *
     * @param file 要获取类型的文件。
     * @return 文件类型枚举。
     */
    public static com.dozenx.common.util.FileType getFileType(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        byte[] head = new byte[4];
        if (-1 == inputStream.read(head)) {
            return com.dozenx.common.util.FileType.UNKNOWN;
        }
        inputStream.close();
        int headHex = 0;
        for (byte b : head) {
            headHex <<= 8;
            headHex |= b;
        }
        switch (headHex) {
            case 0x504B0304:
                return com.dozenx.common.util.FileType.ZIP;
            default:
                return FileType.UNKNOWN;
        }
    }

    public static void decompressTar(String tar, String desc) {
        int resultCode=0;
        try {
            String command = "tar -xf " + tar + " -C " + desc;
             new CmdUtil().execCommand(command);
        } catch (IOException  e) {
            // 异常处理  或许打印  或许统一处理
        }
        if (resultCode != 0) {
            // 异常处理  或许打印  或许统一处理
        }
    }

    public static String convertCygwinPath(String path) {
        path = path.replaceAll("\\\\", "/");
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            path = "/cygdrive/" + path.replaceFirst(":", "");
        return path;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File forceMkdirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        } else if (!file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
        return file;
    }

    public static File forceMkdirs(String pathName) {
        return forceMkdirs(new File(pathName));
    }

    public static File forceMkdirs(File parent, String child) {
        return forceMkdirs(new File(parent, child));
    }

    public static File forceMkdirs(String parent, String child) {
        return forceMkdirs(new File(parent, child));
    }
//    public static void main(String[] args) throws Exception {
//        final String fileType = getFileType("E:/读书笔记/Java编程思想读书笔记.docx");
//        System.out.println(fileType);
//    }


    public static int indexOfExtension(final String filename) {
        if (filename == null) {
            return NOT_FOUND;
        }
        final int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        final int lastSeparator = indexOfLastSeparator(filename);
        return lastSeparator > extensionPos ? NOT_FOUND : extensionPos;
    }

    private static final int NOT_FOUND = -1;

    /**
     * The extension separator character.
     * @since 1.4
     */
    public static final char EXTENSION_SEPARATOR = '.';


    public static int indexOfLastSeparator(final String filename) {
        if (filename == null) {
            return NOT_FOUND;
        }
        final int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        final int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }


    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    public static String getExtension(final String filename) {
        if (filename == null) {
            return null;
        }
        final int index = indexOfExtension(filename);
        if (index == NOT_FOUND) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
}
