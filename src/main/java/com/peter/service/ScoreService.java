package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Score;

public interface ScoreService {
    public PageInfo<Score> getOrderedScores(int pc, int ps);
}
