package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Dataset;
import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.service.DatasetService;
import com.peter.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

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

    @Autowired
    private GrowningAiConfig growningAiConfig;

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
        if (dire == null || dire.length == 0|| StringUtils.isEmpty(dire[0].getOriginalFilename())) {
            LOG.info("files in project to upload is null,upload failed!");
            return "error";
        }
        Arrays.stream(dire).forEach(dir->{
            String originalFilename = dir.getOriginalFilename();
            String name = dir.getName();
            long size = dir.getSize();
            String line = String.join(",", originalFilename, name, size+"");
            System.out.println(line);
        });
        String datasetRoot = growningAiConfig.getDatasetRoot();

        String[] pathNames = dire[0].getOriginalFilename().split("/");
        dataset.setPath(datasetRoot+ File.separator+pathNames[0]);
        File datasetPathDir = new File(dataset.getPath());
        if (datasetPathDir.exists()){
            LOG.info("dataset path in "+dataset.getPath()+" is existed!");
            return "error";
        }
        StringBuilder uploadFiles = new StringBuilder();
        uploadFiles.append("upload files:[");
        for (MultipartFile file : dire) {
            uploadFiles.append(file.getOriginalFilename());
        }
        uploadFiles.append("]");
        LOG.info(uploadFiles.toString());
        String msg = FileUtil.save(datasetRoot, dire);
        if (!msg.equals("上传成功")) {
            LOG.info(msg);
            return "error";
        }

        boolean add = datasetService.add(dataset);
        LOG.info("add dataset:"+dataset);
        return add?"succeed":"error";
    }
    @PostMapping("update")
    @ResponseBody
    public String adminUpdate(@RequestParam("dire") MultipartFile[] dire,Dataset dataset){
        LOG.info(dataset);
        Dataset datasetById = datasetService.get(dataset.getId());
        if (dataset.equals(datasetById)){
            LOG.info("dataset info not changed!");
            return "error";
        }
        if (dire == null || dire.length == 0|| StringUtils.isEmpty(dire[0].getOriginalFilename())) {
            LOG.info("files in project to upload is null,upload failed!");
        }else {
            String datasetRoot = growningAiConfig.getDatasetRoot();
            String[] pathNames = dire[0].getOriginalFilename().split("/");
            dataset.setPath(datasetRoot+ File.separator+pathNames[0]);
            File datasetPathDir = new File(dataset.getPath());
            if (datasetPathDir.exists()){
                LOG.info("dataset path in "+dataset.getPath()+" is existed!");
            }else {
                StringBuilder uploadFiles = new StringBuilder();
                uploadFiles.append("upload files:[");
                for (MultipartFile file : dire) {
                    uploadFiles.append(file.getOriginalFilename());
                }
                uploadFiles.append("]");
                LOG.info(uploadFiles.toString());
                String msg = FileUtil.save(datasetRoot, dire);
                if (!msg.equals("上传成功")) {
                    LOG.info(msg);
                    return "error";
                }
            }
        }
        boolean update = datasetService.update(dataset);
        return update?"succeed":"error";
    }
}
