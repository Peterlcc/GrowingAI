package com.peter.service;

import com.peter.bean.User;

import java.util.List;

public interface UserService {
    List<User> findByName(String username);
    User findById(Integer id);
    String add(User user);
}