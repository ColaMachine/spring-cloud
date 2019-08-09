package com.dozenx.common.util;



import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipUtil {

	/**
	 * 根据提供的输入文件集合和输出文件地址，生成打包的zip文件
	 * @param fileNames
	 * @param zipFilePath
	 * @throws IOException
	 * @author 宋展辉
	 */
	public static void filesToZip(List<String> fileNames,String zipFilePath) throws IOException {
		byte[] buffer = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));
		//根据传入的文件路径集合获取各个文件流放入zip的文件流
		for (int i = 0; i < fileNames.size(); i++) {
			File file = new File(fileNames.get(i));
			if(!file.exists()){
				continue;
			}
			FileInputStream fis = new FileInputStream(file);
			out.putNextEntry(new ZipEntry(file.getName()));
			int len;
			// 读入需要下载的文件的内容，打包到zip文件
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
		}
		out.close();
	}

	public static void fileListToZip(List<File> fileNames,String zipFilePath) throws IOException {
		byte[] buffer = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));
		//根据传入的文件路径集合获取各个文件流放入zip的文件流
		for (int i = 0; i < fileNames.size(); i++) {
			File file = fileNames.get(i);
			if(!file.exists()){
				continue;
			}
			FileInputStream fis = new FileInputStream(file);
			out.putNextEntry(new ZipEntry(file.getName()));
			int len;
			// 读入需要下载的文件的内容，打包到zip文件
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
		}
		out.close();
	}

	public static void foldToZip(String foldPath,String zipFilePath) throws IOException {
		byte[] buffer = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));
		//根据传入的文件路径集合获取各个文件流放入zip的文件流
		File file = new File(foldPath);
		if(!file.exists()){
			throw new IOException("foldPath:"+foldPath +"not exist");
		}
		addFileToZip(out,Paths.get(foldPath).getParent(),new File(foldPath));
		out.close();
	}


	public static void addFileToZip(ZipOutputStream out , Path currentPath, File file) throws IOException {

		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i=0;i<files.length;i++){
				addFileToZip( out ,  currentPath,  files[i]);
			}
		}else {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			String relativePath =currentPath.relativize(Paths.get(file.getPath())).toString();
			System.out.println(relativePath);
			out.putNextEntry(new ZipEntry(relativePath));
			int len;
			// 读入需要下载的文件的内容，打包到zip文件
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
		}
	}
}
