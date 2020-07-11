package com.peter.service;

import com.peter.bean.Admin;

/**
 * @author lcc
 * @date 2020/7/11 17:44
 */
public interface AdminService {
    Admin getById(Integer id);
    boolean delete(Integer id);

    boolean update(Admin admin);
    boolean add(Admin admin);

    Admin getByName(String name);
}
