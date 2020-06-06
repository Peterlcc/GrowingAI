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
    @GetMapping("design/model")
    public String designModel(Model model){
        LOG.info("design/model.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/model";
    }
    @GetMapping("design/node")
    public String designNode(Model model){
        LOG.info("design/node.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/node";
    }
    @GetMapping("design/system")
    public String designSystem(Model model){
        LOG.info("design/system.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/system";
    }
    @GetMapping("design/move")
    public String designMove(Model model){
        LOG.info("design/move.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/move";
    }
    @GetMapping("design/move1")
    public String plumb(Model model){
        LOG.info("design/move1.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/move1";
    }
    @GetMapping("design/movetest")
    public String plumbtest(Model model){
        LOG.info("design/movetest.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/movetest";
    }
    @GetMapping("design/moveevent")
    public String moveevent(Model model){
        LOG.info("design/moveevent.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/moveevent";
    }
    @GetMapping("design/osate")
    public String osate(Model model){
        LOG.info("design/osate.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        return "design/osate";
    }
}
