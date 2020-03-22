package com.peter.service.impl;

import com.peter.bean.Dataset;
import com.peter.mapper.DatasetMapper;
import com.peter.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    private DatasetMapper datasetMapper;
    @Override
    public Dataset getDatasetById(Integer id) {
        return datasetMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Dataset> getDatasets() {
        List<Dataset> datasets = datasetMapper.selectByExample(null);
        return datasets;
    }
}
