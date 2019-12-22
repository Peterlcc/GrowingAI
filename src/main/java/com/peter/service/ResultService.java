package com.peter.service;

import com.peter.bean.Result;

import java.util.List;

public interface ResultService {
    boolean save(Result result);

    boolean save(List<Result> res);
}
