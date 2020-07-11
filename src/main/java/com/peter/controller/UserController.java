package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Project;
import com.peter.bean.User;
import com.peter.service.ProjectService;
import com.peter.service.TestService;
import com.peter.service.UserService;
import com.peter.utils.MessageUtil;
import com.peter.utils.ObjectUtils;
import com.peter.utils.VerifyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(allowCredentials="true",allowedHeaders="*")
@Controller
@RequestMapping("user")
public class UserController {
    private Log LOG = LogFactory.getLog(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
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

    @PutMapping("modify")
    public String modify(User user, HttpServletRequest request, @RequestParam("oldPassword") String oldPassword,
                         @RequestParam("retypePassword") String retypePassword, RedirectAttributes model) {
        User loginUser = (User) request.getSession().getAttribute("user");


        if (!StringUtils.equals(loginUser.getPassword(), oldPassword)) {
            model.addFlashAttribute(MessageUtil.EDIT_MSG, "用户密码输入错误，请确认是本人操作");
        } else if (loginUser.equalsWithModify(user)) {
            model.addFlashAttribute(MessageUtil.EDIT_MSG, "用户信息未作修改");
        } else if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getName())) {
            model.addFlashAttribute(MessageUtil.EDIT_MSG, "用户名或邮箱不能为空");
        } else if (!StringUtils.equals(user.getPassword(), retypePassword)) {
            model.addFlashAttribute(MessageUtil.EDIT_MSG, "两次输入密码不一致");
        } else {
            loginUser.setName(user.getName());
            loginUser.setEmail(user.getEmail());
            if (!StringUtils.isEmpty(user.getPassword())) loginUser.setPassword(user.getPassword());
            userService.update(loginUser);
            model.addFlashAttribute(MessageUtil.EDIT_MSG, "用户信息修改成功");
        }
        return "redirect:/userEdit";
    }

    @GetMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return "redirect:/login";
    }

    @GetMapping("projects")
    public String projects(HttpServletRequest request, Model model){
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
        User user = (User) request.getSession().getAttribute("user");
        PageInfo<Project> pageInfo = projectService.getProjectsByUserId(pc, ps, user.getId());
        LOG.info("list:"+pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        //model.addAttribute("fieldNames", ObjectUtils.getFieldNames(Project.class));
        return "user/list";
    }
    @DeleteMapping("project")
    @ResponseBody
    public String deleteProject(@RequestParam("id")Integer id){
        LOG.info("deleteProject , id is:"+id);
        projectService.delete(id);
        return "删除成功";
    }

    @PostMapping("list")
    @ResponseBody
    public String adminUserList(@RequestParam("draw") int draw,@RequestParam("length") int length,@RequestParam("start") int start){
        int pageCurrent = start / length + 1;
        int pageSize=length;
        PageInfo<User> userPageInfo = userService.getUsers(pageCurrent,pageSize);
        JSONObject result = new JSONObject();
        result.put("draw",draw);
        result.put("recordsTotal",userPageInfo.getTotal());
        result.put("recordsFiltered",userPageInfo.getTotal());
        result.put("data",userPageInfo.getList());
        LOG.info("ajax forward: user list");
        return result.toString();
    }

    @GetMapping("attrs")
    @ResponseBody
    public String adminUserAttrs(){
        Class<User> userClass = User.class;
        Field[] declaredFields = userClass.getDeclaredFields();
        JSONObject jsonObject = new JSONObject();
        List<String> attrs = ObjectUtils.getFieldNames(User.class);
        attrs.remove("password");
        attrs.remove("LOG");
        jsonObject.put("attrs",attrs);
        System.out.println(attrs);
        return jsonObject.toString();
    }

    @GetMapping("delete")
    @ResponseBody
    public String adminDelete(Integer id){
        LOG.info("id:"+id);
        boolean delete = userService.delete(id);
        return delete?"succeed":"error";
    }
    @GetMapping("user")
    @ResponseBody
    public User adminGet(Integer id){
        LOG.info("id:"+id);
        User user = userService.get(id);
        user.setPassword("");
        return user;
    }
    @PostMapping("add")
    @ResponseBody
    public String adminAdd(User user){
        user.setLoginTime(new Date());
        LOG.info(user);
        if (StringUtils.isEmpty(user.getPassword())){
            user.setPassword(null);
        }
        boolean add = userService.adminAdd(user);
        return add?"succeed":"error";

    }
    @PostMapping("update")
    @ResponseBody
    public String adminUpdate(User user){
        User userSource = userService.get(user.getId());
        if (StringUtils.isEmpty(user.getPassword())){
            user.setPassword(userSource.getPassword());
        }
        user.setLoginTime(userSource.getLoginTime());
        LOG.info(user);
        boolean update = userService.update(user);
        return update?"succeed":"error";
    }
}
