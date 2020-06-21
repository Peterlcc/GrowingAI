package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Announce;
import com.peter.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lcc
 * @date 2020/6/21 1:16
 */
@RequestMapping("announcement")
@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("list")
    @ResponseBody
    public String adminAnnouncementList(@RequestParam("draw") int draw, @RequestParam("length") int length, @RequestParam("start") int start){
        int pageCurrent = start / length + 1;
        int pageSize=length;
        PageInfo<Announce> announcementPageInfo = announcementService.getAnnouncements(pageCurrent, pageSize);
        JSONObject result = new JSONObject();
        result.put("draw",draw);
        result.put("recordsTotal",announcementPageInfo.getTotal());
        result.put("recordsFiltered",announcementPageInfo.getTotal());
        result.put("data",announcementPageInfo.getList());
        return result.toString();
    }

    @GetMapping("delete")
    public String adminDelete(@RequestParam("id") int id){
        System.out.println("delete result id="+id);
        return "redirect:/admin/announcement/list";
    }
}
