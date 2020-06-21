package com.peter.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Result;
import com.peter.service.ResultService;
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
    private Log LOG = LogFactory.getLog(ResultController.class);

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
            if (result.getDatasetId()==0)
            {
                LOG.info("result of project "+result.getProjectId()+" has no dataset!");
            }
        }
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
    public String adminDelete(@RequestParam("id") int id){
        System.out.println("delete result id="+id);
        return "redirect:/admin/result/list";
    }

    @PostMapping("add")
    @ResponseBody
    public String add(Result result){
//        boolean save = resultService.save(result);
//        return save?"succeed":"failed";
        System.out.println(result);
        return "ok";
    }
}
