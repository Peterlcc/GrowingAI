package com.peter.service;

import com.peter.bean.User;

import java.util.List;

public interface UserService {
    List<User> findByName(String username);
    List<User> findByNumber(String number);
    List<User> findByEmail(String email);
    User findById(Integer id);
    String add(User user);
    String regist(User user);

    void update(User user);
}