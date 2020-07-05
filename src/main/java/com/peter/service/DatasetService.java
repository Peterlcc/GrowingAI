package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Dataset;

import java.util.List;

public interface DatasetService {
    PageInfo<Dataset> getDatasets(int pc, int ps);
    List<Dataset> getAllSimpleDatasets();

    Dataset get(Integer id);
    boolean update(Dataset dataset);
    boolean add(Dataset dataset);
    boolean delete(Integer id);
}
