package com.peter.controller;

import com.peter.bean.User;
import com.peter.service.UserService;
import com.peter.utils.MessageUtil;
import com.peter.utils.VerifyUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/code")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Object[] images = VerifyUtil.createImage();
        request.getSession().setAttribute("valicode", images[0]);
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
        if (StringUtils.isEmpty(valicodeFromSession)||StringUtils.isEmpty(valicode)||!StringUtils.equals(valicode,valicodeFromSession)){
            model.addFlashAttribute(MessageUtil.LOGIN_MSG, "验证码错误");
            return "redirect:/login";
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            model.addFlashAttribute(MessageUtil.LOGIN_MSG, "用户名密码不能为空");
            return "redirect:/login";
        }
        List<User> users = userService.findByName(username);
        if (users != null && users.size() == 1) {
            User user = users.get(0);
            if (StringUtils.equals(user.getPassword(), password)) {
                request.getSession().setAttribute("user", user);
                return "redirect:/home";
            } else {
                model.addFlashAttribute("msg", "用户名或密码错误");
            }
        } else {
            model.addFlashAttribute("msg", "用户名或密码错误");
        }
        return "redirect:/login";
    }
}
