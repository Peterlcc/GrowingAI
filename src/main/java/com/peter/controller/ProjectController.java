package com.peter.controller;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Project;
import com.peter.service.ProjectService;
import com.peter.utils.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("project")
public class ProjectController {
    private Log LOG = LogFactory.getLog(ProjectController.class);
    @Autowired
    private ProjectService projectService;

    @GetMapping("projects")
    public String getProjects(HttpServletRequest request, Model model){
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
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("fieldNames", ObjectUtils.getFieldNames(Project.class));
        return "project/list";
    }
}

