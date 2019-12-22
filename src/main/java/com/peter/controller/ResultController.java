package com.peter.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.peter.bean.Result;
import com.peter.service.ResultService;
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
        List<Result> res = JSONObject.parseArray(results, Result.class);
        LOG.info("get results:"+res);
        if(resultService.save(res)){
            return "保存成功";
        }else {
            return "保存失败";
        }
    }
}
