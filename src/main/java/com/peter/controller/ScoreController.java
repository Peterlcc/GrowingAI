package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Score;
import com.peter.service.ScoreService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/6/21 1:13
 */
@RequestMapping("score")
@Controller
public class ScoreController {
    private Log LOG = LogFactory.getLog(ScoreController.class);
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
        LOG.info(scorePageInfo.getList());
        return result.toString();
    }

    @PostMapping("rank")
    @ResponseBody
    public String adminProjectRank(@RequestParam("draw") int draw,@RequestParam("length") int length,@RequestParam("start") int start){
        int pageCurrent = start / length + 1;
        int pageSize=length;
        PageInfo<Map<String,Object>> orderedScores = scoreService.getAdminOrderedScores(pageCurrent, pageSize);
        JSONObject result = new JSONObject();
        result.put("draw",draw);
        result.put("recordsTotal",orderedScores.getTotal());
        result.put("recordsFiltered",orderedScores.getTotal());
        result.put("data",orderedScores.getList());
        LOG.info(orderedScores.getList());
        return result.toString();
    }

    @PostMapping("add")
    @ResponseBody
    public String adminAdd(Score score){
        LOG.info(score);
        boolean add = scoreService.add(score);
        return add?"succeed":"error";
    }
    @PostMapping("update")
    @ResponseBody
    public String adminUpdate(Score score){
        LOG.info(score);
        boolean update = scoreService.update(score);
        return update?"succeed":"error";
    }
    @GetMapping("score")
    @ResponseBody
    public Score adminGet(Integer id){
        LOG.info("id:"+id);
        Score score = scoreService.get(id);
        return score;
    }
    @GetMapping("delete")
    @ResponseBody
    public String adminDelete(Integer id){
        LOG.info("id:"+id);
        boolean delete = scoreService.delete(id);
        return delete?"succeed":"error";
    }

    @GetMapping("baidu")
    public String toBaidu(){
        return "redirect:https://www.baidu.com";
    }

}
