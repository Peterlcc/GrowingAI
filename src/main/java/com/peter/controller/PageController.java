package com.peter.controller;

import com.peter.service.TestService;
import com.peter.service.TypeService;
import com.peter.utils.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    private Log LOG = LogFactory.getLog(PageController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TypeService typeService;

    @GetMapping("index")
    public String index() {
        LOG.info("index.html requested!");
        return "index";
    }

    private void checkMsg(String msgName,Model model) {
        String msg = (String) request.getAttribute("msg");
        if (!StringUtils.isEmpty(msg)) {
            model.addAttribute(msgName, msg);
        }
    }

    @GetMapping("login")
    public String login(Model model) {
        LOG.info("login.html requested!");
        checkMsg(MessageUtil.LOGIN_MSG,model);
        return "user/login";
    }

    @GetMapping("list")
    public String list() {
        LOG.info("project.list.html requested!");
        return "project/list";

    }
    @GetMapping("userList")
    public String userList() {
        LOG.info("user.list.html requested!");
        return "user/list";
    }
    @GetMapping("show")
    public String show() {
        return "show";
    }

    @GetMapping("exception")
    public String exception() {
        int i = 10 / 0;
        return "error/404";
    }

    @GetMapping("regist")
    public String regist(Model model) {
        LOG.info("regist.html requested!");
        checkMsg(MessageUtil.REGIST_MSG,model);
        return "user/regist";
    }
    @GetMapping("userEdit")
    public String userEdit(Model model) {
        LOG.info("user.edit.html requested!");
        checkMsg(MessageUtil.EDIT_MSG,model);
        return "user/edit";
    }
    @GetMapping("projectAdd")
    public String addProject(Model model){
        LOG.info("project.upload.html requested!");
        model.addAttribute("types",typeService.getTypes());
        checkMsg(MessageUtil.UPLOAD_MSG,model);
        return "project/upload";
    }
    @GetMapping("projectDetail")
    public String projectDetail(Model model){
        LOG.info("project.detail.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "project/detail";
    }


    /**
     * 后台首页
     * @param model
     * @return
     */
    @GetMapping("/admin/index")
    public String adminIndex(Model model){
        LOG.info("admin.index.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "admin/index";
    }

    /**
     * 数据集列表
     * @param model
     * @return
     */
    @GetMapping("/admin/dataset/list")
    public String adminDataSetList(Model model){
        LOG.info("admin.dataset.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","数据集列表");
        model.addAttribute("searchName","名称");
        return "admin/common/list";
    }

    /**
     * 项目列表
     * @param model
     * @return
     */
    @GetMapping("/admin/project/list")
    public String adminProjectList(Model model){
        LOG.info("admin.project.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","项目列表");
        model.addAttribute("searchName","项目名");
        return "admin/common/list";
    }

    /**
     * 系统用户列表
     * @param model
     * @return
     */
    @GetMapping("/admin/user/list")
    public String adminUserList(Model model){
        LOG.info("admin.user.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","用户列表");
        model.addAttribute("searchName","用户名");
        return "admin/common/list";
    }

    /**
     * 测试结果列表
     * @param model
     * @return
     */
    @GetMapping("/admin/result/list")
    public String adminResultList(Model model){
        LOG.info("admin.result.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","测试结果列表");
        model.addAttribute("searchName","项目名称");
        return "admin/common/list";
    }
    @GetMapping("/admin/result/list1")
    public String adminResultList1(Model model){
        LOG.info("admin.result.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
//        model.addAttribute("title","测试结果列表");
//        model.addAttribute("searchName","项目名称");
        return "admin/list";
    }

    /**
     * 公告列表
     * @param model
     * @return
     */
    @GetMapping("/admin/announcement/list")
    public String adminAnnouncementList(Model model){
        LOG.info("admin.announcement.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","公告列表");
        model.addAttribute("searchName","公告标题");
        return "admin/common/list";
    }

    /**
     * 分数列表
     */
    @GetMapping("/admin/score/list")
    public String adminScoreList(Model model){
        LOG.info("admin.score.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","分数列表");
        model.addAttribute("searchName","用户名");
        return "admin/common/list";
    }
}
