package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Score;

import java.util.Map;

public interface ScoreService {
    PageInfo<Score> getOrderedScores(int pc, int ps);
    PageInfo<Map<String,Object>> getAdminOrderedScores(int pc, int ps);

    boolean add(Score score);
    boolean update(Score score);
    boolean delete(Integer id);
    Score get(Integer id);
}
