package com.peter.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Result;
import com.peter.component.GrowningAiConfig;
import com.peter.component.ProjectTaskQueue;
import com.peter.component.TestTask;
import com.peter.service.ProjectService;
import com.peter.service.ResultService;
import com.peter.service.TestService;
import com.peter.utils.LinuxCmdUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("result")
public class ResultController {
    private static final Log LOG = LogFactory.getLog(ResultController.class);

    @Autowired
    private GrowningAiConfig growningAiConfig;

    @Autowired
    private ResultService resultService;

    @PostMapping("save")
    @ResponseBody
    public String saveResult(@RequestParam("results")  String results){
        LinuxCmdUtils.killShell();
        LOG.info("shell.sh was killed!");
        LOG.info("json:"+results);
        List<Result> res = JSONObject.parseArray(results, Result.class);
        LOG.info("get results:"+res);
        for (Result result:res)
        {
            if (result.getDatasetId()==null||result.getDatasetId().equals(growningAiConfig.getDefaultDataset()))
            {
                LOG.info("result of project "+result.getProjectId()+" has no dataset!");
                result.setDatasetId(growningAiConfig.getDefaultDataset());
            }
        }
        //TODO 设置结果是否成功

        if(resultService.save(res)){
            return "保存成功";
        }else {
            return "保存失败";
        }
    }

    @PostMapping("list")
    @ResponseBody
    public String adminResultList(@RequestParam("draw") int draw, @RequestParam("length") int length, @RequestParam("start") int start){
        int pageCurrent = start / length + 1;
        int pageSize=length;
        PageInfo<Result> resultPageInfo = resultService.getResults(pageCurrent, pageSize);
        JSONObject result = new JSONObject();
        result.put("draw",draw);
        result.put("recordsTotal",resultPageInfo.getTotal());
        result.put("recordsFiltered",resultPageInfo.getTotal());
        result.put("data",resultPageInfo.getList());
        return result.toString();
    }

    @GetMapping("delete")
    @ResponseBody
    public String adminDelete(@RequestParam("id") int id){
        boolean res = resultService.deleteResult(id);
        LOG.info("result "+id+" deleted!");
        return res?"succeed":"error";
    }

    @PostMapping("add")
    @ResponseBody
    public String adminAdd(Result result){
        boolean add = resultService.add(result);
        LOG.info(result);
        return add?"succeed":"failed";
    }

    @PostMapping("update")
    @ResponseBody
    public String adminUpdate(Result result){
        LOG.info(result);
        boolean save = resultService.updateResult(result);
        return save?"succeed":"failed";
    }
    @GetMapping("result")
    @ResponseBody
    public Result adminResult(Integer id){
        Result result = resultService.getResult(id);
        LOG.info(result);
        return result;
    }
}
