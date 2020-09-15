package com.peter.controller;

import com.peter.bean.*;
import com.peter.service.*;
import com.peter.utils.BeanEnum;
import com.peter.utils.MessageUtil;
import com.peter.utils.ObjectUtils;
import com.peter.utils.PageModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
//@CrossOrigin(allowCredentials="true",allowedHeaders="*")
@Controller
public class PageController {
    private Log LOG = LogFactory.getLog(PageController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TypeService typeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private TaskUploadService taskUploadService;
    @Autowired
    private FileTypeService fileTypeService;

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

        model.addAttribute("taskSize",taskUploadService.getProjectsInTask());
    }

    @GetMapping("login")
    public String login(Model model) {
        LOG.info("login.html requested!");
        checkMsg(MessageUtil.LOGIN_MSG,model);
        PageModelUtil.loginRegisterAttr(model,"登录","login");
        return "user/login-register";
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
    @GetMapping("modal")
    public String show() {
        return "admin/common/modal";
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
        PageModelUtil.loginRegisterAttr(model,"注册","register");
        return "user/login-register";
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
    @GetMapping("/aadl/add")
    public String aadlAdd(Model model){
        LOG.info("user.uploadFile.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("fileType",fileTypeService.getById(1));
        return "user/uploadFile";
    }


    /**
     * 系统管理员登录
     * @param model
     * @return
     */
    @GetMapping("/admin/login")
    public String admimLogin(Model model) {
        LOG.info("/admin/login.html requested!");
        checkMsg(MessageUtil.LOGIN_MSG,model);
        model.addAttribute("title","后台登录");
        return "admin/login";
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

        List<String> attrs = ObjectUtils.getFieldNames(Dataset.class);
        List<String> chinaAttrs = ObjectUtils.getChinaAttrs(BeanEnum.DATASET, attrs);
        chinaAttrs.add("操作");
        model.addAttribute("attrs",chinaAttrs);
        List<Type> types = typeService.getTypes();
        Type type = typeService.getTypeById(1);
        types.remove(type);
        model.addAttribute("types",types);
        return "admin/dataset/datasetList";
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
        List<String> attrs = ObjectUtils.getFieldNames(Project.class);
        attrs.remove("user");
        attrs.remove("type");
        attrs.remove("results");
        List<String> chinaAttrs = ObjectUtils.getChinaAttrs(BeanEnum.PROJECT, attrs);
        chinaAttrs.add("操作");
        model.addAttribute("attrs",chinaAttrs);

        List<User> simpleUsers = userService.getSimpleUsers();
        model.addAttribute("users",simpleUsers);

        List<Type> types = typeService.getTypes();
        model.addAttribute("types",types);
        return "admin/project/projectList";
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
        List<String> attrs = ObjectUtils.getFieldNames(User.class);
        attrs.remove("password");
        attrs.remove("LOG");
        List<String> chinaAttrs = ObjectUtils.getChinaAttrs(BeanEnum.USER, attrs);
        chinaAttrs.add("操作");
        model.addAttribute("attrs",chinaAttrs);
        return "admin/user/userList";
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
        List<String> attrs = ObjectUtils.getFieldNames(Result.class);
        List<String> chinaAttrs = ObjectUtils.getChinaAttrs(BeanEnum.RESULT, attrs);
        chinaAttrs.add("操作");
        model.addAttribute("attrs",chinaAttrs);

        List<Project> simpleProjects = projectService.getAllSimpleProjects();
        model.addAttribute("projects",simpleProjects);

        List<Dataset> simpleDatasets = datasetService.getAllSimpleDatasets();
        model.addAttribute("datasets",simpleDatasets);
        return "admin/result/resultList";
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
        List<String> attrs = ObjectUtils.getFieldNames(Announce.class);
        List<String> chinaAttrs = ObjectUtils.getChinaAttrs(BeanEnum.ANNOUNCEMENT, attrs);
        chinaAttrs.add("操作");
        model.addAttribute("attrs",chinaAttrs);
        return "admin/announcement/announcementList";
    }
    /**
     * 公告内容
     * @param model
     * @return
     */
    @GetMapping("/admin/announcement/detail/{method}")
    public String adminAnnouncementDetail(@PathVariable("method")String method, Model model){
        LOG.info("admin.announcement.list.html requested!,method is "+method);
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","公告详情");
        if (StringUtils.contains(method,"query")){
            Integer id = Integer.valueOf(method.substring(5));
            Announce announce = announcementService.get(id);
            LOG.info("get: "+announce);
            model.addAttribute("announce",announce);
        }
        return "admin/announcement/announcementDetail";
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
        List<String> attrs = ObjectUtils.getFieldNames(Score.class);
        attrs.remove("project");
        List<String> chinaAttrs = ObjectUtils.getChinaAttrs(BeanEnum.SCORE, attrs);
        chinaAttrs.add("操作");
        model.addAttribute("attrs",chinaAttrs);

        List<Project> simpleProjects = projectService.getAllSimpleProjects();
        model.addAttribute("projects",simpleProjects);

        return "admin/score/scoreList";
    }


    /**
     * 排名列表
     */
    @GetMapping("/admin/score/rank")
    public String adminScoreRank(Model model){
        LOG.info("admin.rank.list.html requested!");
        checkMsg(MessageUtil.DETAIL_MSG,model);
        model.addAttribute("title","用户项目排名");
        model.addAttribute("searchName","项目名名");
        List<String> chinaAttrs = Arrays.asList("项目名称","项目得分","作者","项目创建时间");
        model.addAttribute("attrs",chinaAttrs);

//        List<Project> simpleProjects = projectService.getAllSimpleProjects();
//        model.addAttribute("projects",simpleProjects);

        return "admin/score/rankList";
    }

    public static void main(String[] args) {
        String method="query5";
        Integer id = Integer.valueOf(method.substring(5));
        System.out.println(id);
    }
}
