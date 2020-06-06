package com.peter.service.impl;

import com.peter.bean.Model;
import com.peter.bean.UserModel;
import com.peter.bean.UserModelExample;
import com.peter.mapper.UserModelMapper;
import com.peter.service.UserModelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcc
 * @date 2020/5/17 13:41
 */
@Service
public class UserModelServiceImpl implements UserModelService {
    private Log LOG = LogFactory.getLog(UserModelServiceImpl.class);

    @Autowired
    private UserModelMapper userModelMapper;

    @Override
    public UserModel getModelById(Integer id) {
        UserModel userModel = userModelMapper.selectByPrimaryKey(id);
        return userModel;
    }

    @Override
    public List<UserModel> getAll() {
        List<UserModel> userModels = userModelMapper.selectByExample(null);
        return userModels;
    }

    @Override
    public void save(UserModel model) {
        UserModelExample userModelExample=new UserModelExample();
        UserModelExample.Criteria criteria = userModelExample.createCriteria();
        criteria.andNameEqualTo(model.getName());
        List<UserModel> userModels = userModelMapper.selectByExample(userModelExample);
        if (userModels==null||userModels.size()==0){
            userModelMapper.insertSelective( model);
        }else {
            model.setId(userModels.get(0).getId());
            userModelMapper.updateByPrimaryKey(model);
        }
    }
}
