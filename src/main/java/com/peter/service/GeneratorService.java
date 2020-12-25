package com.peter.service;

import com.peter.bean.File;
import com.peter.bean.User;

import java.util.Map;

/**
 * @author lcc
 * @date 2020/8/9 17:57
 */
public interface GeneratorService {
    File generate(File file, User user, Map<String, String> params);
}
