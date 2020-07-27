package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Announce;
import com.peter.service.AnnouncementService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lcc
 * @date 2020/6/21 1:16
 */
@RequestMapping("announcement")
@Controller
public class AnnouncementController {
    private Log LOG = LogFactory.getLog(AnnouncementController.class);

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
    @GetMapping("list")
    public String announcementList(HttpServletRequest request,Model model){
        LOG.info("announcement.list.html requested!");
        String pcstr = request.getParameter("pc");
        if (pcstr == null || pcstr.equals("")) {
            pcstr = "1";
        }
        int pc = Integer.parseInt(pcstr);
        String psstr = request.getParameter("ps");
        if (psstr == null || psstr.equals("")) {
            psstr = "10";
        }
        int ps = Integer.parseInt(psstr);
        PageInfo<Announce> pageInfo = announcementService.getAnnouncements(pc, ps);
        pageInfo.getList().forEach(announce -> announce.setContent(null));
        LOG.info("get projects list,size:"+pageInfo.getList().size());
        model.addAttribute("pageInfo", pageInfo);
        return "announcement/announcementList";
    }
    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id")Integer id,Model model){
        LOG.info("announcement.detail.html requested!");
        Announce announce = announcementService.get(id);
        LOG.info("get "+announce);
        model.addAttribute("announce",announce);
        return "announcement/detail";
    }

    @PostMapping("announcement")
    @ResponseBody
    public String save(Announce announce){
        LOG.info("save announce:"+announce);
        announce.setCreateTime(new Date());
        boolean save = announcementService.save(announce);
        return save?"succeed":"failed";
    }
    @PostMapping("update")
    @ResponseBody
    public String update(Announce announce){
        LOG.info("update announce:"+announce);
        announce.setCreateTime(new Date());
        boolean save = announcementService.update(announce);
        return save?"succeed":"failed";
    }
    @GetMapping("delete")
    @ResponseBody
    public String adminDelete(@RequestParam("id") int id){
        LOG.info("delete result id="+id);
        boolean delete = announcementService.delete(id);
        return delete?"succeed":"failed";
    }


}
