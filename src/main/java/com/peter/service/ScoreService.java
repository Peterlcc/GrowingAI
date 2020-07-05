package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Score;

public interface ScoreService {
    PageInfo<Score> getOrderedScores(int pc, int ps);

    boolean add(Score score);
    boolean update(Score score);
    boolean delete(Integer id);
    Score get(Integer id);
}
