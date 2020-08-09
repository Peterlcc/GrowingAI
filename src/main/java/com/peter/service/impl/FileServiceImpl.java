package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.File;
import com.peter.bean.FileExample;
import com.peter.bean.FileType;
import com.peter.mapper.FileMapper;
import com.peter.mapper.FileTypeMapper;
import com.peter.mapper.UserMapper;
import com.peter.service.FileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/8/9 11:36
 */
@Service
public class FileServiceImpl implements FileService {
    private Log LOG = LogFactory.getLog(FileServiceImpl.class);

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileTypeMapper fileTypeMapper;

    @Override
    public void save(File file) {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(file.getName());
        criteria.andUserIdEqualTo(file.getUserId());
        criteria.andTypeIdEqualTo(file.getTypeId());
        List<File> list = fileMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            LOG.info("project to save is not existed! then insert");
            fileMapper.insertSelective(file);
        }else {
            LOG.info("project to save is existed! then update");
            fileMapper.updateByPrimaryKey(file);
        }
    }

    @Override
    public File getById(Integer id) {
        File file = fileMapper.selectByPrimaryKey(id);
        return file;
    }

    @Override
    public PageInfo<File> getFilesByUserId(int pc, int ps, Integer userId,Integer type) {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andTypeIdEqualTo(type);
        criteria.andUserIdEqualTo(userId);
        PageHelper.startPage(pc, ps);
        List<File> files = fileMapper.selectByExample(example);
        if (files == null || files.size() == 0) {
            LOG.info("getFilesByUserId,no file of user :" + userId);
        }
        files.stream().forEach(file -> {
            file.setUser(userMapper.selectByPrimaryKey(file.getUserId()));
            file.setFileType(fileTypeMapper.selectByPrimaryKey(file.getTypeId()));
        });
        LOG.info("getFilesByUserId pc:" + pc + " ps:" + ps + " size:" + files.size());
        PageInfo<File> filePageInfo=new PageInfo<>(files);
        return filePageInfo;
    }

    @Override
    public PageInfo<File> getFiles(int pc, int ps,Integer type) {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andTypeIdEqualTo(type);
        PageHelper.startPage(pc, ps);
        List<File> files = fileMapper.selectByExample(example);
        if (files == null || files.size() == 0) {
            LOG.info("getFilesByUserId,no file of type :"+type);
        }
        files.stream().forEach(file -> {
            file.setUser(userMapper.selectByPrimaryKey(file.getUserId()));
            file.setFileType(fileTypeMapper.selectByPrimaryKey(file.getTypeId()));
        });
        LOG.info("getFilesByUserId pc:" + pc + " ps:" + ps + " size:" + files.size());
        PageInfo<File> filePageInfo=new PageInfo<>(files);
        return filePageInfo;
    }

    @Override
    public boolean update(File file) {
        int i = fileMapper.updateByPrimaryKey(file);
        return i==1;
    }

    @Override
    public boolean delete(Integer id) {
        int i = fileMapper.deleteByPrimaryKey(id);
        return i==1;
    }
}
