package com.peter.controller;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Project;
import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.service.ProjectService;
import com.peter.service.TaskUploadService;
import com.peter.utils.FileUtil;
import com.peter.utils.MessageUtil;
import com.peter.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;
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
    private GrowningAiConfig growningAiConfig;

    @Autowired
    private TaskUploadService taskUploadService;

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
        LOG.info("list:"+pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("fieldNames", ObjectUtils.getFieldNames(Project.class));
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
        String path=growningAiConfig.getUploadPath() +File.separator;
        String pname = dire[0].getOriginalFilename().substring(0, dire[0].getOriginalFilename().indexOf("/"));
        project.setPath(path+ pname);
        System.out.println(project);
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
        taskUploadService.upload(project);
        LOG.info("save project:" + project.toString());
        return "redirect:/project/projects";
    }
    @GetMapping("project/{id}")
    public String getProjectDetail(@PathVariable("id") Integer id, Model model){
        Project project = projectService.getProjectDetail(id);
        if (project==null){
            model.addAttribute(MessageUtil.DETAIL_MSG,"项目查询失败！");
        }else {
            model.addAttribute("project",project);
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
}

