package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<Dataset> getDatasets(int pc, int ps) {
        PageHelper.startPage(pc,ps);
        List<Dataset> datasets = datasetMapper.selectByExample(null);
        PageInfo<Dataset> datasetPageInfo = new PageInfo<>(datasets);
//        datasetPageInfo.setList(datasets);
        return datasetPageInfo;
    }

    @Override
    public List<Dataset> getAllSimpleDatasets() {
        List<Dataset> datasets = datasetMapper.selectSimpleByExample(null);
        return datasets;
    }
}
