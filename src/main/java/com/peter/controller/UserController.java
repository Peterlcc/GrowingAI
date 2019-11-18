package com.peter.controller;

import com.peter.bean.User;
import com.peter.service.UserService;
import com.peter.utils.MessageUtil;
import com.peter.utils.VerifyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    private Log LOG = LogFactory.getLog(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/code")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Object[] images = VerifyUtil.createImage();
        request.getSession().setAttribute("valicode", images[0]);
        LOG.info("valicode:" + images[0] + " generated");
        BufferedImage image = (BufferedImage) images[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);

    }

    @PostMapping("login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("valicode") String valicode,
                        RedirectAttributes model, HttpServletRequest request) {
        String valicodeFromSession = (String) request.getSession().getAttribute("valicode");
        LOG.info("post msg:[username:" + username + ",passwd:" + password + ",code:" + valicode + "]");
        if (StringUtils.isEmpty(valicodeFromSession) || StringUtils.isEmpty(valicode) || !StringUtils.equalsIgnoreCase(valicode, valicodeFromSession)) {
            model.addFlashAttribute(MessageUtil.LOGIN_MSG, "验证码错误");
            LOG.info("验证码错误");
            return "redirect:/login";
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            model.addFlashAttribute(MessageUtil.LOGIN_MSG, "用户名密码不能为空");
            LOG.info("用户名密码不能为空");
            return "redirect:/login";
        }
        List<User> users = userService.findByName(username);
        if (users != null && users.size() == 1) {
            User user = users.get(0);
            if (StringUtils.equals(user.getPassword(), password)) {
                user.setLoginTime(new Date());
                userService.update(user);
                request.getSession().setAttribute("user", user);
                return "redirect:/index";
            } else {
                model.addFlashAttribute(MessageUtil.LOGIN_MSG, "用户名或密码错误");
            }
        } else {
            model.addFlashAttribute(MessageUtil.LOGIN_MSG, "用户名或密码错误");
        }
        LOG.info("用户名或密码错误");
        return "redirect:/login";
    }

    @PostMapping("regist")
    public String regist(User user, @RequestParam("valicode") String valicode, HttpServletRequest request,
                         @RequestParam("retypePassword") String retypePassword, RedirectAttributes model) {
        user.setLoginTime(new Date());
        LOG.info("user regist:" + user + ", valicode:" + valicode + ", retypePassword" + retypePassword);
        String valicodeFromSession = (String) request.getSession().getAttribute("valicode");
        if (!StringUtils.equalsIgnoreCase(valicode, valicodeFromSession)) {
            model.addFlashAttribute(MessageUtil.LOGIN_MSG, "验证码不正确");
            return "redirect:/regist";
        }
        if (!StringUtils.equals(retypePassword, user.getPassword())) {
            model.addFlashAttribute(MessageUtil.REGIST_MSG, "两次密码不一致");
            return "redirect:/regist";
        }
        String registMsg = userService.regist(user);
        if (StringUtils.equals(registMsg, "注册成功")) {
            model.addFlashAttribute(MessageUtil.REGIST_MSG, "注册成功，请登录");
            return "redirect:/login";
        } else {
            model.addFlashAttribute(MessageUtil.REGIST_MSG, registMsg);
            return "redirect:/regist";
        }
    }

    @PostMapping("modify")
    public String modify(User user, HttpServletRequest request,
                         @RequestParam("retypePassword") String retypePassword, RedirectAttributes model) {
        User loginUser = (User) request.getSession().getAttribute("user");
        if (loginUser.equals(user)){
            model.addFlashAttribute(MessageUtil.EDIT_MSG,"用户信息未作修改");
        }else if(!StringUtils.equals(user.getPassword(),retypePassword)){
            model.addFlashAttribute(MessageUtil.EDIT_MSG,"两次输入密码不一致");
        }else {
            user.setId(loginUser.getId());
            user.setLoginTime(new Date());
            userService.update(user);
            request.getSession().setAttribute("user",user);
            model.addFlashAttribute(MessageUtil.EDIT_MSG,"用户信息修改成功");
        }
        return "redirect:/userEdit";
    }

    @GetMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return "redirect:/login";
    }
}
