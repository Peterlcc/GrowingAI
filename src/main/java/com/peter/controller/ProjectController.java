package com.peter.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Project;
import com.peter.bean.Score;
import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.service.ProjectService;
import com.peter.service.ScoreService;
import com.peter.service.TaskUploadService;
import com.peter.service.UserService;
import com.peter.utils.FileUtil;
import com.peter.utils.MessageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("project")
public class ProjectController {
    private Log LOG = LogFactory.getLog(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private GrowningAiConfig growningAiConfig;

    @Autowired
    private TaskUploadService taskUploadService;

    @Autowired
    private UserService userService;

    @GetMapping("projects")
    public String getProjects(HttpServletRequest request, Model model) {
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
        PageInfo<Project> pageInfo = projectService.getProjects(pc, ps);
        LOG.info("get projects list,size:"+pageInfo.getList().size());
        model.addAttribute("pageInfo", pageInfo);
        //model.addAttribute("fieldNames", ObjectUtils.getFieldNames(Project.class));
        return "project/list";
    }

    @PostMapping("project")
    public String upload(@RequestParam("dire") MultipartFile[] dire, Project project, HttpServletRequest request,
                         RedirectAttributes model) {
        if (dire == null || dire.length == 0) {
            LOG.info("files in project to upload is null,upload failed!");
            model.addFlashAttribute(MessageUtil.UPLOAD_MSG,"上传项目的文件为空，请确认上传的项目！");
            return "redirect:/projectAdd";
        }
        User user = (User) request.getSession().getAttribute("user");
        project.setUserId(user.getId());
        project.setCreateTime(new Date());
        String path=growningAiConfig.getUploadPath() +File.separator+user.getNumber()+File.separator;
//        String pname = dire[0].getOriginalFilename().substring(0, dire[0].getOriginalFilename().indexOf("/"));
        String[] pathNames = dire[0].getOriginalFilename().split("/");
        project.setPath(path+ pathNames[0]);
        File projectPathDir = new File(project.getPath());
        if (projectPathDir.exists()){
            LOG.info("projectPathDir is existed,upload failed!");
            model.addFlashAttribute(MessageUtil.UPLOAD_MSG,"上传项目的已存在，请确认上传的项目！");
            return "redirect:/projectAdd";
        }
        //System.out.println(project);
        StringBuilder uploadFiles = new StringBuilder();
        uploadFiles.append("upload files:[");
        for (MultipartFile file : dire) {
            uploadFiles.append(file.getOriginalFilename());
        }
        uploadFiles.append("]");
        LOG.info(uploadFiles.toString());
        String msg = FileUtil.save(path, dire);
        if (!msg.equals("上传成功")) {
            LOG.info(msg);
            model.addFlashAttribute(MessageUtil.UPLOAD_MSG,msg);
            return "redirect:/projectAdd";
        }

        projectService.save(project);
//        taskUploadService.upload(project);
        LOG.info("save project:" + project.toString());
        return "redirect:/project/projects";
    }
    @GetMapping("project/{id}")
    public String getProjectDetail(@PathVariable("id") Integer id, Model model,HttpServletRequest request){
        Project project = projectService.getProjectDetail(id);
        Project projectByIdWithUser = projectService.getProjectByIdWithUser(id);
        Integer userId = projectByIdWithUser.getUserId();
        User user = (User) request.getSession().getAttribute("user");

        if (project==null){
            model.addAttribute(MessageUtil.DETAIL_MSG,"项目查询失败！");
        }else {
            model.addAttribute("project",project);
            model.addAttribute("own",user.getId().equals(userId));
        }
        return "project/detail";
    }
    @GetMapping("struct")
    @ResponseBody
    public List<Map<String, Object>> getStruct(@RequestParam("id")Integer id){
        List<Map<String, Object>> struct = projectService.getStructById(id);
        if (struct==null){
            LOG.info("getStruct is null");
            return null;
        }
        LOG.info("getStruct is:"+ struct);
        return struct;
    }
    @GetMapping("file")
    @ResponseBody
    public String getFileDetail(@RequestParam("path")String path){
        try {
            String fileDetail = FileUtils.readFileToString(new File(path), "utf8");
            return fileDetail;
        } catch (IOException e) {
            LOG.error("getFileDetail error "+e.getMessage());
            return "打开文件失败";
        }
    }
    @PostMapping("file")
    @ResponseBody
    public String setFileDetail(@RequestParam("path")String path,@RequestParam("text")String text){
        try {
            FileUtils.writeStringToFile(new File(path),text,"utf8");
            LOG.info(path+" is updated!");
            return "succeed";
        } catch (IOException e) {
            LOG.error("getFileDetail error "+e.getMessage());
            return "打开文件失败";
        }
    }

    @GetMapping("top")
    public String selectAllprojects(HttpServletRequest request, Model model) {
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

        //排过序的分数，分数有project的信息，project有用户的信息
        PageInfo<Score> pageInfo = scoreService.getOrderedScores(pc, ps);
        LOG.info("ordered score list:"+pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        //model.addAttribute("fieldNames", ObjectUtils.getFieldNames(Project.class));
        return "project/top";
    }

    @PostMapping("list")
    @ResponseBody
    public String adminProjectList(@RequestParam("draw") int draw,@RequestParam("length") int length,@RequestParam("start") int start){
        int pageCurrent = start / length + 1;
        int pageSize=length;
        PageInfo<Project> projectPageInfo = projectService.getProjects(pageCurrent, pageSize);
        JSONObject result = new JSONObject();
        result.put("draw",draw);
        result.put("recordsTotal",projectPageInfo.getTotal());
        result.put("recordsFiltered",projectPageInfo.getTotal());
        result.put("data",projectPageInfo.getList());
        return result.toString();
    }

    @GetMapping("delete")
    @ResponseBody
    public String adminDelete(int id){
        LOG.info("id:"+id);
        boolean delete = projectService.delete(id);
        return delete?"succeed":"error";
    }
    @GetMapping("project")
    @ResponseBody
    public Project adminGet(Integer id){
        Project project = projectService.getProjectById(id);
        return project;
    }
    @PostMapping("add")
    @ResponseBody
    public String adminAdd(@RequestParam("dire") MultipartFile[] dire, Project project){
//        projectService.save(project);
        if (dire == null || dire.length == 0|| StringUtils.isEmpty(dire[0].getOriginalFilename())) {
            LOG.info("files in project to upload is null,upload failed!");
            return "error";
        }
        User user = userService.get(project.getUserId());
        String path=growningAiConfig.getUploadPath() +File.separator+user.getNumber()+File.separator;
//        String pname = dire[0].getOriginalFilename().substring(0, dire[0].getOriginalFilename().indexOf("/"));
        String[] pathNames = dire[0].getOriginalFilename().split("/");
        project.setPath(path+ pathNames[0]);
        File projectPath = new File(project.getPath());
        if (projectPath.exists()){
            LOG.info("project path in "+project.getPath()+" is existed!");
            return "error";
        }
        //System.out.println(project);
        StringBuilder uploadFiles = new StringBuilder();
        uploadFiles.append("upload files:[");
        for (MultipartFile file : dire) {
            uploadFiles.append(file.getOriginalFilename());
        }
        uploadFiles.append("]");
        LOG.info(uploadFiles.toString());
        String msg = FileUtil.save(path, dire);
        if (!msg.equals("上传成功")) {
            LOG.info(msg);
            return "error";
        }
        project.setCreateTime(new Date());
        projectService.save(project);
//        taskUploadService.upload(project);
        LOG.info("save project:" + project.toString());
        return "succeed";
    }
    @PostMapping("update")
    @ResponseBody
    public String adminUpdate(@RequestParam("dire") MultipartFile[] dire,Project project){
        Project projectById = projectService.getProjectById(project.getId());
        if (projectById.equals(project)) {
            LOG.info("project info not changed!");
            return "error";
        }
        if (dire == null || dire.length == 0|| StringUtils.isEmpty(dire[0].getOriginalFilename())) {
            LOG.info("files in project to upload is null,path no need to update");
        }else {
            User user = userService.get(project.getUserId());
            String path = growningAiConfig.getUploadPath() + File.separator + user.getNumber() + File.separator;
            String[] pathNames = dire[0].getOriginalFilename().split("/");
            project.setPath(path + pathNames[0]);
            File projectPath = new File(project.getPath());
            if (projectPath.exists()) {
                LOG.info("project path in " + project.getPath() + " is existed!");
            } else {
                //System.out.println(project);
                StringBuilder uploadFiles = new StringBuilder();
                uploadFiles.append("upload files:[");
                for (MultipartFile file : dire) {
                    uploadFiles.append(file.getOriginalFilename());
                }
                uploadFiles.append("]");
                LOG.info(uploadFiles.toString());
                String msg = FileUtil.save(path, dire);
                if (!msg.equals("上传成功")) {
                    LOG.info(msg);
                    return "error";
                }
            }
        }
        boolean update = projectService.update(project);
        LOG.info("update project:" + project.toString());
        return update?"succeed":"error";
    }
}