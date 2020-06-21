package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Result;

import java.util.List;

public interface ResultService {
    PageInfo<Result> getResults(int pc,int ps);
    boolean save(Result result);

    boolean save(List<Result> res);
}
