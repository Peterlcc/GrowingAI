package com.peter.service.impl;

import com.peter.bean.Type;
import com.peter.mapper.TypeMapper;
import com.peter.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type getTypeById(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Type> getTypes() {
        return typeMapper.selectByExample(null);
    }
}
