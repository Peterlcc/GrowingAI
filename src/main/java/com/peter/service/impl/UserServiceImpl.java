package com.peter.service.impl;

import com.peter.bean.User;
import com.peter.bean.UserExample;
import com.peter.mapper.UserMapper;
import com.peter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findByName(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andNameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public String add(User user) {
        List<User> users = findByName(user.getName());
        if (users != null && users.size() > 0) {
            return "用户名已存在";
        } else {
            int i = userMapper.insert(user);
            if (i == 1) {
                return "注册成功，请登录";
            } else {
                return "注册失败，请联系管理员";
            }
        }
    }
}
