package com.peter.service.impl;

import com.peter.bean.FileType;
import com.peter.mapper.FileTypeMapper;
import com.peter.service.FileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lcc
 * @date 2020/8/9 12:17
 */
@Service
public class FileTypeServiceImpl implements FileTypeService {
    @Autowired
    private FileTypeMapper fileTypeMapper;
    @Override
    public FileType getById(Integer id) {
        FileType fileType = fileTypeMapper.selectByPrimaryKey(id);
        return fileType;
    }
}
