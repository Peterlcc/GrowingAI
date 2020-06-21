package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Dataset;

import java.util.List;

public interface DatasetService {
    Dataset getDatasetById(Integer id);
    PageInfo<Dataset> getDatasets(int pc, int ps);
}
