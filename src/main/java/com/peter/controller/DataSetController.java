package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Dataset;
import com.peter.service.DatasetService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lcc
 * @date 2020/6/20 18:27
 */
@RequestMapping("dataset")
@Controller
public class DataSetController {
    private Log LOG = LogFactory.getLog(DataSetController.class);

    @Autowired
    private DatasetService datasetService;

    @PostMapping("list")
    @ResponseBody
    public String adminDatasetList(@RequestParam("draw") int draw, @RequestParam("length") int length, @RequestParam("start") int start){
        int pageCurrent = start / length + 1;
        int pageSize=length;
        PageInfo<Dataset> datasetPageInfo = datasetService.getDatasets(pageCurrent, pageSize);
        JSONObject result = new JSONObject();
        result.put("draw",draw);
        result.put("recordsTotal",datasetPageInfo.getTotal());
        result.put("recordsFiltered",datasetPageInfo.getTotal());
        result.put("data",datasetPageInfo.getList());
        return result.toString();
    }

    @GetMapping("delete")
    @ResponseBody
    public String adminDelete( Integer id){
        LOG.info("id:"+id);
        boolean delete = datasetService.delete(id);
        return delete?"succeed":"error";
    }
    @GetMapping("dataset")
    @ResponseBody
    public Dataset adminGet(Integer id){
        LOG.info("id:"+id);
        Dataset dataset = datasetService.get(id);
        dataset.setPath(null);
        return dataset;
    }
    @PostMapping("add")
    @ResponseBody
    public String adminAdd(@RequestParam("dire") MultipartFile[] dire, Dataset dataset){
        LOG.info(dataset);
        //TODO upload files
        boolean add = datasetService.add(dataset);
        return add?"succeed":"error";
    }
    @PostMapping("update")
    @ResponseBody
    public String adminUpdate(@RequestParam("dire") MultipartFile[] dire,Dataset dataset){
        LOG.info(dataset);
        //TODO upload files
        boolean update = datasetService.update(dataset);
        return update?"succeed":"error";
    }
}
