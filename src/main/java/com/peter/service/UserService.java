package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.User;

import java.util.List;

public interface UserService {
    List<User> findByName(String username);
    List<User> findByNumber(String number);
    List<User> findByEmail(String email);
    List<User> getSimpleUsers();
    String add(User user);
    PageInfo<User> getUsers(int pc,int ps);

    String regist(User user);


    User get(Integer id);
    boolean adminAdd(User user);
    boolean update(User user);
    boolean delete(Integer id);
}