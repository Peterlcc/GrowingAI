package com.peter.controller;

import com.peter.bean.Result;
import com.peter.service.ResultService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;

@Controller
@RequestMapping("result")
public class ResultController {
    private Log LOG = LogFactory.getLog(ResultController.class);

    @Autowired
    private ResultService resultService;
    @PostMapping("save")
    @ResponseBody
    public String saveResult(Result result){
        LOG.info("get result:"+result);
        if(resultService.save(result)){
            return "保存成功";
        }else {
            return "保存失败";
        }
    }
}
