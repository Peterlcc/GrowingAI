package com.peter.service;

import com.peter.bean.SysModel;

import java.util.List;

/**
 * @author lcc
 * @date 2020/5/17 13:38
 */
public interface SysModelService {
    SysModel getModelById(Integer id);
    List<SysModel> getAll();
    void save(SysModel model);
}
