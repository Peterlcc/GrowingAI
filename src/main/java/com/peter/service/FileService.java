package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.File;

/**
 * @author lcc
 * @date 2020/8/9 11:33
 */
public interface FileService {
    void save(File file);
    File getById(Integer id);
    PageInfo<File> getFilesByUserId(int pc, int ps,Integer userId,Integer type);
    PageInfo<File> getFiles(int pc, int ps,Integer type);
    boolean update(File file);
    boolean delete(Integer id);
}
