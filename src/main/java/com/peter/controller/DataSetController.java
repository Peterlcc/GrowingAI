package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Dataset;
import com.peter.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lcc
 * @date 2020/6/20 18:27
 */
@RequestMapping("dataset")
@Controller
public class DataSetController {
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
    public String adminDelete(@RequestParam("id") int id){
        System.out.println("delete result id="+id);
        return "redirect:/admin/dataset/list";
    }
}
