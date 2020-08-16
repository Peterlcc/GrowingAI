package com.peter.utils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private final static String deletePath="F:/tmp";
	public final static File deleteDir=new File(deletePath);
	public static String save(String dir,MultipartFile file) {
		try {
			File dire = new File(dir);
			if (!dire.exists()) {
				dire.mkdirs();
			}
			String originalFilename = file.getOriginalFilename();
			File destFile = new File(dir+originalFilename);
			if (destFile.exists()) {
				return "文件已存在";
			}
			else {
				file.transferTo(destFile);
				return "上传成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "上传失败";
		}
	}

	public static String save(String basePath, MultipartFile[] dire) {
		if (basePath.endsWith("/")) {
			basePath=basePath.substring(0, basePath.length()-1);
		}
		for (MultipartFile file : dire) {
			String filePath=basePath+File.separator+file.getOriginalFilename();
			File destFile=new File(filePath);
			if (destFile.exists()) {
				return "文件已存在";
			}
			else {
				mkdirs(filePath);
				try {
					file.transferTo(destFile);
				} catch (IOException e) {
					e.printStackTrace();
					return filePath+"上传失败,上传终止";
				}
			}
		}
		return "上传成功";
	}

	private static void mkdirs(String filePath) {
		if (filePath.lastIndexOf("/")>0) {
			String direPath=filePath.substring(0, filePath.lastIndexOf("/"));
			File direFile = new File(direPath);
			if (!direFile.exists()) {
				direFile.mkdirs();
			}
		}
	}
	private static long id=0;
	public static List<Map<String,Object>> getPathStruct(String path){
		File root = new File(path);
		if (root.exists()&&root.isDirectory()){
			id=1;
			return construct(root);
		}
		return null;
	}

	private static List<Map<String,Object>> construct(File root) {
		File[] files = root.listFiles();
		if (files==null) return null;
		List<Map<String,Object>> struct=new ArrayList<>();
		for (File file:files){
			if (file.isFile()){
				Map<String,Object> fileMap=new HashMap<>();
//				fileMap.put("id",id);
				id=id+1;
				fileMap.put("id",file.getAbsolutePath());
				fileMap.put("name",file.getName());
				fileMap.put("type","leaf");
				struct.add(fileMap);
			}else{
				Map<String,Object> dirMap=new HashMap<>();
				dirMap.put("id",id);
				id=id+1;
				dirMap.put("name",file.getName());
				List<Map<String, Object>> structChild = construct(file);
				if(structChild!=null) dirMap.put("child",structChild);
				struct.add(dirMap);
			}
		}
		return struct;
	}


	/**
	 * 压缩当前目录path下的所有文件 , 生成文件名称为 zipname , 放在路径zippath下 ;
	 * 有异常则抛出 ;
	 * @param path
	 * @param zippath
	 * @param zipname
	 * @throws IOException
	 */
	public static void folder2zip(String path, String zippath, String zipname) {
		File src = new File(path);
		ZipOutputStream zos = null;


		if (src == null  || !src.exists() || !src.isDirectory()) {
			// 源目录不存在 或不是目录 , 则异常
			throw new RuntimeException("压缩源目录不存在或非目录!" + path);
		}

		File destdir = new File(zippath);

		if (!destdir.exists()) {
			// 创建目录
			destdir.mkdirs();
		}

		File zipfile = new File(zippath+File.separator+zipname);


		File[] srclist = src.listFiles();

		if (srclist == null || srclist.length == 0) {
			// 源目录内容为空,无需压缩
			throw new RuntimeException("源目录内容为空,无需压缩下载!" + path);
		}

		try {
			zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipfile)));

			// 递归压缩目录下所有的文件  ;
			compress(zos, src, src.getName());

			zos.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException("压缩目标文件不存在!" + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("压缩文件IO异常!" + e.getMessage());
		}
		finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

	}

	/**
	 * 递归压缩文件
	 * @param zos
	 * @param src
	 * @throws IOException
	 */
	private static void compress(ZipOutputStream zos, File src, String name) throws IOException {
		if (src == null || !src.exists()) {
			return ;
		}
		if (src.isFile()) {
			byte[] bufs = new byte[10240];

			ZipEntry zentry = new ZipEntry(name);
			zos.putNextEntry(zentry);

			FileInputStream in = new FileInputStream(src);

			BufferedInputStream bin = new BufferedInputStream(in, 10240);

			int readcount = 0 ;

			while( (readcount = bin.read(bufs, 0 , 10240)) != -1) {
				zos.write(bufs, 0 , readcount);
			}

			zos.closeEntry();
			bin.close();
		} else {
			// 文件夹
			File[] fs = src.listFiles();

			if (fs == null || fs.length == 0 ) {
				zos.putNextEntry(new ZipEntry(name + File.separator ));
				zos.closeEntry();
				return ;
			}

			for (File f : fs) {
				compress(zos, f, name + File.separator + f.getName());
			}
		}
	}



	public static void main(String[] args) {
		List<Map<String, Object>> struct = getPathStruct("F:\\tmp\\src\\test123\\algorithm_test");
		JSONArray objects = new JSONArray(struct);

		System.out.println(struct);
		System.out.println(objects.toString());
	}
}
