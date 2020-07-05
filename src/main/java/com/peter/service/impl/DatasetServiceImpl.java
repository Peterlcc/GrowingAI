package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Dataset;
import com.peter.bean.DatasetExample;
import com.peter.mapper.DatasetMapper;
import com.peter.service.DatasetService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    private DatasetMapper datasetMapper;

    @Override
    public Dataset get(Integer id) {
        Dataset dataset=datasetMapper.selectByPrimaryKey(id);
        return dataset;
    }

    @Override
    public boolean update(Dataset dataset) {
        int i = datasetMapper.updateByPrimaryKeySelective(dataset);
        return i==1;
    }

    @Override
    public boolean add(Dataset dataset) {
        DatasetExample datasetExample = new DatasetExample();
        DatasetExample.Criteria criteria = datasetExample.createCriteria();
        criteria.andNameEqualTo(dataset.getName());
        List<Dataset> datasets = datasetMapper.selectByExample(datasetExample);
        if (!CollectionUtils.isEmpty(datasets)){
            return false;
        }
        int i = datasetMapper.insertSelective(dataset);
        return i==1;
    }

    @Override
    public boolean delete(Integer id) {
        int i = datasetMapper.deleteByPrimaryKey(id);
        return i==1;
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
