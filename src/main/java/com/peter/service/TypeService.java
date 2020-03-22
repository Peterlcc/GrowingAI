package com.peter.service;

import com.peter.bean.Type;

import java.util.List;

public interface TypeService {
    Type getTypeById(Integer id);
    List<Type> getTypes();
}
