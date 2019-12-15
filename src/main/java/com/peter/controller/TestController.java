package com.peter.controller;

import com.peter.bean.Project;
import com.peter.service.ProjectService;
import com.peter.service.TaskUploadService;
import com.peter.service.TestService;
import com.peter.utils.LinuxCmdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {
    private Log LOG = LogFactory.getLog(TestController.class);
    @Autowired
    private TestService testService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskUploadService taskUploadService;
    @GetMapping("cmd")
    @ResponseBody
    public String testCmd(@RequestParam("cmd")String cmd){
        //编译所有node
        String compileCommand="source /opt/ros/kinetic/setup.bash && source /root/WorkSpaces/catkin_ws/devel/setup.sh && "+cmd;
        if(LinuxCmdUtils.executeLinuxCmdWithPath(compileCommand,"/root/WorkSpaces/catkin_ws")){
            LOG.info("命令运行成功");
        }else {
            LOG.error("命令运行出错");
        }
        return "cmd is "+compileCommand;
    }
    @GetMapping("add")
    @ResponseBody
    public String add(@RequestParam("id")Integer id){
        Project project = projectService.getProjectById(id);
        taskUploadService.upload(project);
        return "uploaded";
    }
}
