package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Score;
import com.peter.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lcc
 * @date 2020/6/21 1:13
 */
@RequestMapping("score")
@Controller
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("list")
    @ResponseBody
    public String adminScoreList(@RequestParam("draw") int draw, @RequestParam("length") int length, @RequestParam("start") int start){
        int pageCurrent = start / length + 1;
        int pageSize=length;
        PageInfo<Score> scorePageInfo = scoreService.getOrderedScores(pageCurrent, pageSize);
        JSONObject result = new JSONObject();
        result.put("draw",draw);
        result.put("recordsTotal",scorePageInfo.getTotal());
        result.put("recordsFiltered",scorePageInfo.getTotal());
        result.put("data",scorePageInfo.getList());
        return result.toString();
    }
    @GetMapping("delete")
    public String adminDelete(@RequestParam("id") int id){
        System.out.println("delete result id="+id);
        return "redirect:/admin/score/list";
    }
}
