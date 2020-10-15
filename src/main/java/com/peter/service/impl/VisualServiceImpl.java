package com.peter.service.impl;

import com.peter.utils.RedisUtil;
import com.peter.service.VisualService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author lcc
 * @date 2020/10/14 下午4:47
 */
@Service
@Slf4j
public class VisualServiceImpl implements VisualService {
    private static final String keyPrefix="res_";
    @Autowired
    private RedisUtil algorithmRedisUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Object> getLatestAlgorithmDatas() {
        Set<String> keys = redisTemplate.keys(keyPrefix+"*");
        int maxIndex=-1;
        for (String key : keys) {
            int number = Integer.parseInt(key.substring(4));
            maxIndex=Math.max(maxIndex,number);
        }
        String key=keyPrefix+maxIndex;
        List<Object> algorithmDatas = algorithmRedisUtil.lGet(key, 0, algorithmRedisUtil.lGetListSize(key) - 1);

        return algorithmDatas;
    }
}
