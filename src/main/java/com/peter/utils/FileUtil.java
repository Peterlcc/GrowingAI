package com.peter.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private final static String deletePath="F:/tmp";
	public final static File deleteDir=new File(deletePath);
	public static String saveSimple(String dir,MultipartFile file) {
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

	public static void main(String[] args) {
		List<Map<String, Object>> struct = getPathStruct("F:\\tmp\\src\\test123\\algorithm_test");
		JSONArray objects = new JSONArray(struct);

		System.out.println(struct);
		System.out.println(objects.toString());
	}
}
