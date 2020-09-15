package com.peter.controller;

import com.peter.bean.Admin;
import com.peter.component.ProjectTaskQueue;
import com.peter.component.TestTask;
import com.peter.service.AdminService;
import com.peter.utils.LinuxCmdUtils;
import com.peter.utils.MessageUtil;
import com.peter.utils.RunTag;
import com.peter.utils.VerifyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author lcc
 * @date 2020/7/11 17:43
 */
//@CrossOrigin(allowCredentials="true",allowedHeaders="*")
@Controller
@RequestMapping("admin")
public class AdminController {
    private Log LOG = LogFactory.getLog(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProjectTaskQueue projectTaskQueue;

    @Autowired
    private RunTag runTag;

    @GetMapping("/code")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Object[] images = VerifyUtil.createImage();
        request.getSession().setAttribute("adminValicode", images[0]);
        LOG.info("valicode:" + images[0] + " generated");
        BufferedImage image = (BufferedImage) images[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    @PostMapping("login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password,
                         RedirectAttributes model){
        LOG.info("post msg:[name:" + name + ",passwd:" + password + "]");
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            model.addFlashAttribute(MessageUtil.LOGIN_MSG, "用户名密码不能为空");
            LOG.info("用户名密码不能为空");
            return "redirect:/admin/login";
        }
        Admin adminByName = adminService.getByName(name);
        if (adminByName!=null){
            if (StringUtils.equals(adminByName.getPassword(), password)) {
                adminByName.setLoginTime(new Date());
                adminService.update(adminByName);
                request.getSession().setAttribute("admin", adminByName);
                return "redirect:/admin/index";
            }
        }
        LOG.info("用户名或密码错误");
        model.addFlashAttribute(MessageUtil.LOGIN_MSG, "用户名或密码错误");
        return "redirect:/admin/login";
    }

    @GetMapping("logout")
    public String logout(){
        Object admin = request.getSession().getAttribute("admin");
        LOG.info(admin+" logout!");
        request.getSession().setAttribute("admin",null);
        return "redirect:/admin/login";
    }

    @GetMapping("kill")
    public String killTask(){
        runTag.setRunFlag(false);
        projectTaskQueue.pop();
        LinuxCmdUtils.killShell();
        return "测试任务已终止";
    }
}
