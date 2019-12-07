package com.peter.controller;

import org.apache.commons.lang3.StringUtils;
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
    @PostMapping("save")
    @ResponseBody
    public String saveResult(@RequestParam("name")String name,@RequestParam("score")int score){
        System.out.println(name+":"+score);
        return "保存成功";
    }
}
