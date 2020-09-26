package com.peter.utils;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/9/14 上午10:09
 */
public class YamlUtil {
    private static String bootstrap_file = "F:/tmp/application.yml";
    private static Map<String, Object> result = new HashMap<>();

    /**
     * 根据文件名获取yml的文件内容
     *
     * @return
     */
    public static Map<String, Object> getYmlByFileName(String file) {
        result = new HashMap<>();
        if (file == null)
            file = bootstrap_file;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml props = new Yaml();

        Map<String, Object> param = props.loadAs(in, Map.class);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();

            if (val instanceof Map) {
                forEachYaml(key, (Map<String, Object>) val);
            } else {
                result.put(key, val);
            }
        }

        return result;
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        Map<String, Object> map = getYmlByFileName(null);
        if (map == null) return null;
        return map.get(key);
    }

    /**
     * 遍历yml文件，获取map集合
     *
     * @param key_str
     * @param obj
     * @return
     */
    public static Map<String, Object> forEachYaml(String key_str, Map<String, Object> obj) {
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            String str_new = "";
            if (!StringUtils.isEmpty(key_str)) {
                str_new = key_str + "." + key;
            } else {
                str_new = key;
            }
            if (val instanceof Map) {
                forEachYaml(str_new, (Map<String, Object>) val);
            } else {
                result.put(str_new, val);
            }
        }
        return result;
    }

    /**
     * 获取bootstrap.yml的name
     *
     * @return
     */
    public static Object getApplicationName() {
        return getYmlByFileName(bootstrap_file).get("spring.application.name");
    }

    /**
     * 获取bootstrap.yml的name
     *
     * @return
     */
    public static Object getApplicationName1() {
        Object name = getYmlByFileName(bootstrap_file).get("spring.application.name");
        return name + "center";
    }

    public static boolean saveAsYaml(Map<String, Object> map,String path) {

        DumperOptions options = new DumperOptions();

        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml props = new Yaml(options);
        FileWriter fileWriter = null;
        try {
            fileWriter=new FileWriter(path);
            props.dump(map, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
          if (fileWriter!=null){
              try {
                  fileWriter.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
        return true;
    }

    public static void main(String[] args) {
//        System.out.println(getApplicationName());
//        Map<String, Object> map = getYmlByFileName(null);
//        map.forEach((key, val) -> {
//            System.out.println(key + "--" + val);
//        });
//        saveAsYaml(map,"F:/tmp/t.yaml");

        Person michael = new Person();
        Person floveria = new Person();
        Person[] children = new Person[2];
        children[0] = new Person();
        children[1] = new Person();

        michael.setName("Michael Corleone");
        michael.setAge(24);
        floveria.setName("Floveria Edie");
        floveria.setAge(24);
        children[0].setName("boy");
        children[0].setAge(3);
        children[1].setName("girl");
        children[1].setAge(1);

        michael.setSpouse(floveria);
        floveria.setSpouse(michael);

        michael.setChildren(children);
        floveria.setChildren(children);


        File dumpFile = new File("F:/tmp/testYaml.yaml");
        Yaml yaml = new Yaml();
        try {
            yaml.dump(michael, new FileWriter(dumpFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
