package com.dozenx.common.util;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class IoUtil {
	
	/**
	 * 
	 * @param fullPathName
	 * @return
	 * @throws IOException 
	 */
	private static String getContentStringFromFile(String fullPathName, String lineSeparator) throws IOException {
		String fileContent = null;
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fullPathName),"UTF-8")); 
			
			String         line = null;
			StringBuilder  stringBuilder = new StringBuilder();
			String         ls = lineSeparator;
			
			while( ( line = reader.readLine() ) != null ) {
			    stringBuilder.append( line );
			    stringBuilder.append( ls );
			}

			fileContent = stringBuilder.toString();
		} catch (IOException e) {
			fileContent = null;
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}

		return fileContent;
	}
	
	public static String getStringFromFile(String fullPathName) throws IOException {
		return IoUtil.getContentStringFromFile(fullPathName, System.lineSeparator());
	}
	
	/**
	 * Read file with block into string (Without line separator)
	 * @param fullPathName
	 * @return
	 * @throws IOException
	 */
	public static String getBlockStringFromFile(String fullPathName) throws IOException {
		//return IoUtil.getContentStringFromFile(fullPathName, "\r\n");
		
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(fullPathName));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
	}
	
	public static void dumpStringToFile(String content, String fullPathName) throws IOException {
		FileWriter fw = new FileWriter( fullPathName);
		BufferedWriter writer = new BufferedWriter(fw );
	    writer.write( content);
	    fw.close();
	    writer.close();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//ini file reader
	public static Map<String, Properties> getContentsFromIni(String fileName) throws IOException {
		Map<String, Properties> sections = null;
		
		String currentSecion = null;
		Properties current = null;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));  
		
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.matches("\\[.*\\]")) {
				currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
				current = new Properties();
				
				if (sections == null) {
					sections = new HashMap<String, Properties>();
				}
				sections.put(currentSecion, current);
			} 
			else if (line.matches(".*=.*")) {
				if (current != null) {
					int i = line.indexOf('=');
					String name = line.substring(0, i);
					String value = line.substring(i + 1);
					current.setProperty(name, value);
				}
			}
		}
		
		reader.close();
		
		return sections;
	}

	public static Properties getSection(Map<String, Properties> sections, String sectionName){
		Properties p = (Properties) sections.get(sectionName);
		return p;
	}
	
	public static String getValue(Map<String, Properties> sections, String section, String name) {
		Properties p = (Properties) sections.get(section);

		if (p == null) {
			return null;
		}

		String value = p.getProperty(name);
		return value;
	}
	
	//////////////////////////////////////////////////////
	// zip utilities
	public static void extractZipNative(File fileZip) {
		System.out.println(fileZip.getAbsolutePath());
		if (fileZip != null && fileZip.exists()) {
			ZipInputStream zis = null;
			try {
				zis = new ZipInputStream(new FileInputStream(fileZip));
				ZipEntry ze = zis.getNextEntry();

				byte[] buffer = new byte[(int) ze.getSize()];


				FileOutputStream fos = new FileOutputStream(IoUtil.getExtractedFilePath(fileZip)
						+ File.separator + ze.getName());

				while ((zis.read(buffer)) > 0) {
					fos.write(buffer);
				}
				fos.flush();
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (zis != null) {
					try {
						zis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static String getExtractedFilePath(File zipFile) {
		String extractedFilePath = null;
		if (zipFile != null && zipFile.exists()) {
			String zipFilePath = zipFile.getParent();
			
			String zipFileName = zipFile.getName().split("\\.")[0];
			
			extractedFilePath = zipFilePath + File.separator + zipFileName;
		}
		
		return extractedFilePath;
	}
	
	/** 
     * 缓存区大小默认20480 
     */  
    private final static int FILE_BUFFER_SIZE = 20480;  
	
	public static boolean unzip(File file){  
		String zipFileName = file.getName().split("\\.")[0];
//		String targetFileDir = file.getParent()+File.separator+zipFileName;
		String targetFileDir = file.getParent();
		
        boolean flag = false;  
        //1.判断压缩文件是否存在，以及里面的内容是否为空  
        ZipFile zipFile = null;
        
        System.out.println(">>>>>>解压文件【" + file.getAbsolutePath() + "】到【" + targetFileDir + "】目录下<<<<<<");  
        if(false == file.exists()) {  
            System.out.println(">>>>>>压缩文件【" + file.getAbsolutePath() + "】不存在<<<<<<");  
            return false;  
        } else if(0 == file.length()) {  
            System.out.println(">>>>>>压缩文件【" + file.getAbsolutePath() + "】大小为0不需要解压<<<<<<");  
            return false;  
        } else {  
            //2.开始解压ZIP压缩文件的处理  
            byte[] buf = new byte[FILE_BUFFER_SIZE];  
            int readSize = -1;  
            ZipInputStream zis = null;  
            FileOutputStream fos = null;  
            try {  
                // 检查是否是zip文件  
                zipFile = new ZipFile(file);  
                zipFile.close();
                
                // 判断目标目录是否存在，不存在则创建  
                File newdir = new File(targetFileDir);  
                if (false == newdir.exists()) {  
                    newdir.mkdirs();  
                    newdir = null;  
                }  
                
                zis = new ZipInputStream(new FileInputStream(file));  
                ZipEntry zipEntry = zis.getNextEntry();  
                
                // 开始对压缩包内文件进行处理  
                while (null != zipEntry) {  
                    String zipEntryName = zipEntry.getName().replace('\\', '/');  
                    
                    //判断zipEntry是否为目录，如果是，则创建  
                    if(zipEntry.isDirectory()) {  
                        int indexNumber = zipEntryName.lastIndexOf('/');  
                        File entryDirs = new File(targetFileDir + File.separator + zipEntryName.substring(0, indexNumber));  
                        entryDirs.mkdirs();  
                        entryDirs = null;  
                    } else {  
                        try {  
                            fos = new FileOutputStream(targetFileDir + File.separator + zipEntryName);  
                            while ((readSize = zis.read(buf, 0, FILE_BUFFER_SIZE)) != -1) {  
                                fos.write(buf, 0, readSize);  
                            }  
                        } catch (Exception e) {  
                            e.printStackTrace();  
                            throw new RuntimeException(e.getCause());   
                        } finally {  
                            try {  
                                if (null != fos) {  
                                    fos.close();  
                                }  
                            } catch (IOException e) {  
                                e.printStackTrace();  
                                throw new RuntimeException(e.getCause());   
                            }  
                        }  
                    }  
                    zipEntry = zis.getNextEntry();  
                }  
                flag = true;  
            } catch (ZipException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e.getCause());   
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e.getCause());   
            } finally {  
                try {  
                    if (null != zis) {  
                        zis.close();  
                    }  
                    if (null != fos) {  
                        fos.close();  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e.getCause());   
                }  
            }  
        }  
        return flag;  
    }
	
	public static boolean moveFile(File oldFile, File newFile){
		return oldFile.renameTo(newFile); 
	}
	
	public static boolean moveFile(File oldFile, String newPath){
		File fnewpath = new File(newPath);
		if(!fnewpath.exists()) {
			fnewpath.mkdirs(); 
		}
		return oldFile.renameTo(new File(newPath + File.separator + oldFile.getName()));
	}
	
	public static boolean moveFileTo(String oldFilePath, String newPath){
		File oldFile = new File(oldFilePath);
		File fnewpath = new File(newPath);
		if(!fnewpath.exists()) {
			fnewpath.mkdirs(); 
		}
		
		return oldFile.renameTo(new File(newPath + File.separator + oldFile.getName())); 
	}
	
	public static boolean moveFilePath(String oldFilePath, String newFilePath){
		File oldFile = new File(oldFilePath);
		File newFile = new File(newFilePath);
		
		File fnewpath = new File(newFile.getParent());
		if(!fnewpath.exists()) {
			fnewpath.mkdirs(); 
		}
		
		return oldFile.renameTo(newFile); 
	}
	
	 /** 
     * 删除文件夹 
     * @param filePathAndName String 文件夹路径及名称 如c:/fqf 
     * @param fileContent String 
     * @return boolean 
     */ 
   public static void delFolder(String folderPath) { 
       try { 
           delAllFile(folderPath); //删除完里面所有内容  
           File folder = new File(folderPath);
           folder.delete(); //删除空文件夹 

       } 
       catch (Exception e) { 
           System.out.println("删除文件夹操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 删除文件夹里面的所有文件 
     * @param path String 文件夹路径 如 c:/fqf 
     */ 
   public static void delAllFile(String path) {
       File file = new File(path); 
       if (!file.exists()) {
           return;
       } 
       if (!file.isDirectory()) {
           return; 
       }
       String[] tempList = file.list(); 
       File temp = null; 
       for (int i = 0; i < tempList.length; i++) { 
           if (path.endsWith(File.separator)) { 
               temp = new File(path + tempList[i]); 
           } 
           else { 
               temp = new File(path + File.separator + tempList[i]); 
           } 
           if (temp.isFile()) { 
               temp.delete(); 
           } 
           if (temp.isDirectory()) { 
               delAllFile(path + File.separator + tempList[i]);//先删除文件夹里面的文件 
               delFolder(path + File.separator + tempList[i]);//再删除空文件夹 
           } 
       } 
   } 
   
   public static void fileUpload(File file, File destFile) throws Exception{
	   InputStream is = new FileInputStream(file);
		OutputStream os = new FileOutputStream(destFile);

		byte[] b = new byte[1024 * 1024 * 10];
		int length = 0;
		while (true) {
			length = is.read(b);
			if (length < 0)
				break;
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	   
   }
   
   /**  
    * 复制整个文件夹内容  
    *   
    * @param oldPath String 原文件路径 如： 
    * @param newPath String 复制后路径 如：
    * @return boolean  
    */  
   public static boolean copyFolder(String oldPath, String newPath) {
	   System.out.println(">>>>>>复制目录【"+oldPath+"】到【"+newPath+"】目录<<<<<<");
       try {
           (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹   
           File a = new File(oldPath);   
           String[] file = a.list();   
           File temp = null;   
           for (int i = 0; i < file.length; i++) {   
               if (oldPath.endsWith(File.separator)) {   
                   temp = new File(oldPath + file[i]);   
               } else {   
                   temp = new File(oldPath + File.separator + file[i]);   
               }   
 
               if (temp.isFile()) {   
                   FileInputStream input = new FileInputStream(temp);   
                   FileOutputStream output = new FileOutputStream(newPath + File.separator + temp.getName());   
                   byte[] b = new byte[1024 * 5];   
                   int len;   
                   while ((len = input.read(b)) != -1) {   
                       output.write(b, 0, len);   
                   }   
                   output.flush();   
                   output.close();   
                   input.close();   
               }   
               if (temp.isDirectory()) {// 如果是子文件夹   
                   copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i]);   
               }   
           }
           System.out.println(">>>>>>复制成功<<<<<<");
           return true;
       } catch (Exception e) {   
           System.out.println("复制整个文件夹内容操作出错 ");   
           e.printStackTrace();   
           return false;
       }   
   }   
 
   /**  
    * 移动文件夹到指定目录  
    *   
    * @param oldPath  
    *            String 如：c:/fqf  
    * @param newPath  
    *            String 如：d:/fqf  
    */  
   public void moveFolder(String oldPath, String newPath) {   
       copyFolder(oldPath, newPath);   
       delFolder(oldPath);   
   }   
   
   public static String FormetFileSize(long fileS) {//转换文件大小
       DecimalFormat df = new DecimalFormat("#.00");
       String fileSizeString = "";
       if (fileS < 1024) {
           fileSizeString = df.format((double) fileS) + "B";
       } else if (fileS < 1048576) {
           fileSizeString = df.format((double) fileS / 1024) + "K";
       } else if (fileS < 1073741824) {
           fileSizeString = df.format((double) fileS / 1048576) + "M";
       } else {
           fileSizeString = df.format((double) fileS / 1073741824) + "G";
       }
       return fileSizeString;
   }
   
   public static String getFileSizeM(long fileS) {//转换文件大小
       DecimalFormat df = new DecimalFormat("#.00");
       String fileSizeString = "";
       
       if (fileS < 1024) {
           fileSizeString = df.format((double) fileS) + "B";
       } else if (fileS < 1048576) {
           fileSizeString = df.format((double) fileS / 1024) + "K";
       } else if (fileS < 1073741824) {
           fileSizeString = df.format((double) fileS / 1048576) + "M";
       } else {
           fileSizeString = df.format((double) fileS / 1073741824) + "G";
       }
       return fileSizeString;
   }
   
   public static String fileValidate(String fileType, String[] fileTypeOptionArray, String fileTypeMsg,
		   long fileSize, long fileSizeMax, String fileSizeMsg){
	   boolean fileTypeValid = false;
	   for(String type : fileTypeOptionArray){
		   if(fileType.equals(type)){
			   fileTypeValid = true;
			   break;
		   }
	   }
	   
	   if(!fileTypeValid) {
		   return fileTypeMsg;
	   }
	   
	   if(fileSize > fileSizeMax){
		   return fileSizeMsg;
	   }
	   
	   return null;
   }
}
