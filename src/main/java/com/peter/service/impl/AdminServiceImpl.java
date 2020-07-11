package com.peter.service.impl;

import com.peter.bean.Admin;
import com.peter.bean.AdminExample;
import com.peter.mapper.AdminMapper;
import com.peter.service.AdminService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/7/11 17:45
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public boolean delete(Integer id) {
        int i = adminMapper.deleteByPrimaryKey(id);
        return i==1;
    }

    @Override
    public boolean update(Admin admin) {
        int i = adminMapper.updateByPrimaryKeySelective(admin);
        return i==1;
    }

    @Override
    public boolean add(Admin admin) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andNameEqualTo(admin.getName());

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (CollectionUtils.isEmpty(admins)){
            int i = adminMapper.insertSelective(admin);
            return i==1;
        }else {
            return false;
        }
    }

    @Override
    public Admin getByName(String name) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andNameEqualTo(name);

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (CollectionUtils.isEmpty(admins)||admins.size()>1)
        {
            return null;
        }else {
            return admins.get(0);
        }
    }
}
