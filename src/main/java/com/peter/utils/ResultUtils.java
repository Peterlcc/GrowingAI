package com.peter.utils;

import com.peter.bean.Result;

/**
 * @author lcc
 * @date 2020/10/21 10:35
 */
public class ResultUtils {

    public static Result getInstance(Integer pid, String msg) {
        Result result = new Result();
        result.setDatasetId(1);
        result.setLength(Double.MAX_VALUE);
        result.setPoints(0);
        result.setTime(Double.MAX_VALUE);
        result.setProjectId(pid);
        result.setContext(msg);
        return result;
    }
    public static Result getError(Integer pid, String msg){
        Result instance = getInstance(pid, msg);
        instance.setSucceed(false);
        return instance;
    }
    public static Result getSucceed(Integer pid, String msg){
        Result instance = getInstance(pid, msg);
        instance.setSucceed(true);
        return instance;
    }
}
