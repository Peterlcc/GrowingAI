package com.peter.service;

import com.peter.bean.Dataset;

import java.util.List;

public interface DatasetService {
    Dataset getDatasetById(Integer id);
    List<Dataset> getDatasets();
}
