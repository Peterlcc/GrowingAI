package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.User;
import com.peter.bean.UserExample;
import com.peter.mapper.UserMapper;
import com.peter.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private Log LOG = LogFactory.getLog(UserServiceImpl.class);
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
    public List<User> findByNumber(String number) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNumberEqualTo(number);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public List<User> findByEmail(String email) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
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

    @Override
    public String regist(User user) {
        if (!user.check()){
            return "用户信息不达要求";
        }
        List<User> users = findByEmail(user.getEmail());
        if (users==null||users.size()>0){
            LOG.info("邮箱已注册");
            return "邮箱已注册";
        }
        users=findByNumber(user.getNumber());
        if (users==null||users.size()>0){
            LOG.info("学号已注册");
            return "学号已注册";
        }

        userMapper.insert(user);
        return "注册成功";
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public PageInfo<User> getUsers(int pc, int ps) {
        PageHelper.startPage(pc, ps);
        List<User> users = userMapper.selectByExample(null);
        users.forEach(user -> user.setPassword(null));
        LOG.info("get users pc:"+pc+" ps:"+ps+" size:"+users.size());
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }
}
