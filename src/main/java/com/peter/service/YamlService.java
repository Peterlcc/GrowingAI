package com.peter.service;

import java.util.Map;

/**
 * @author lcc
 * @date 2020/9/26 12:38
 */
public interface YamlService {
    Map<String,Object> read(String path);
    boolean write(Map<String,Object> yaml,String path);
}
