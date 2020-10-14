package com.peter.utils;

import java.lang.reflect.Field;
import java.util.*;

public class ObjectUtils {
    private final static Map<BeanEnum,Map<String,String>> reflectMap=new HashMap<>();

    private final static Map<String,String> userAttrs=new HashMap<>();
    private final static Map<String,String> projectAttrs=new HashMap<>();
    private final static Map<String,String> datasetAttrs=new HashMap<>();
    private final static Map<String,String> resultAttrs=new HashMap<>();
    private final static Map<String,String> scoreAttrs=new HashMap<>();
    private final static Map<String,String> announcementAttrs=new HashMap<>();

    static {
        reflectMap.put(BeanEnum.USER,userAttrs);
        reflectMap.put(BeanEnum.PROJECT,projectAttrs);
        reflectMap.put(BeanEnum.DATASET,datasetAttrs);
        reflectMap.put(BeanEnum.SCORE,scoreAttrs);
        reflectMap.put(BeanEnum.RESULT,resultAttrs);
        reflectMap.put(BeanEnum.ANNOUNCEMENT,announcementAttrs);

        userAttrs.put("id","ID");
        userAttrs.put("name","姓名");
        userAttrs.put("realName","真实姓名");
        userAttrs.put("number","学号");
        userAttrs.put("email","邮箱");
        userAttrs.put("sex","性别");
        userAttrs.put("loginTime","最近登录时间");

        projectAttrs.put("id","ID");
        projectAttrs.put("name","项目名称");
        projectAttrs.put("path","路径");
        projectAttrs.put("userId","用户ID");
        projectAttrs.put("typeId","类型ID");
        projectAttrs.put("createTime","创建时间");

        datasetAttrs.put("id","ID");
        datasetAttrs.put("name","数据集名称");
        datasetAttrs.put("path","路径");
        datasetAttrs.put("typeid","类型ID");

        resultAttrs.put("id","ID");
        resultAttrs.put("length","长度");
        resultAttrs.put("points","点数");
        resultAttrs.put("time","耗时");
        resultAttrs.put("projectId","项目ID");
        resultAttrs.put("datasetId","数据集ID");

        scoreAttrs.put("id","ID");
        scoreAttrs.put("projectId","项目ID");
        scoreAttrs.put("score","分数");

        announcementAttrs.put("id","ID");
        announcementAttrs.put("title","标题");
        announcementAttrs.put("content","内容");
        announcementAttrs.put("createTime","创建时间");

    }

    @Deprecated
    public static List<String> getUserChinaAttrs(List<String> attrs){
        List<String> res=new ArrayList<>();
        attrs.forEach(attr->res.add(userAttrs.get(attr)));
        return res;
    }
    public static List<String> getChinaAttrs(BeanEnum beanEnum,List<String> attrs){
        List<String> res=new ArrayList<>();
        attrs.forEach(attr->res.add(reflectMap.get(beanEnum).get(attr)));
        return res;
    }
    public static List<String> getFieldNames(Class clazz){

        List<String> fieldNames = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }
}
