package com.peter.service;

import com.peter.bean.UserModel;

import java.util.List;

/**
 * @author lcc
 * @date 2020/5/17 13:38
 */
public interface UserModelService {
    UserModel getModelById(Integer id);
    List<UserModel> getAll();
    void save(UserModel model);
}
