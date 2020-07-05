package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Result;

import java.util.List;

public interface ResultService {
    PageInfo<Result> getResults(int pc,int ps);
    boolean save(Result result);
    boolean add(Result result);

    boolean save(List<Result> res);

    Result getResult(Integer id);
    boolean deleteResult(Integer id);

    boolean updateResult(Result result);
}
