package com.peter.service.impl;

import com.peter.bean.Project;
import com.peter.bean.Result;
import com.peter.component.GrowningAiConfig;
import com.peter.service.TestService;
import com.peter.utils.LinuxCmdUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
public class TestServiceImpl implements TestService {
    private Log LOG = LogFactory.getLog(TestServiceImpl.class);
    @Autowired
    private GrowningAiConfig growningAiConfig;

    @Override
    @Async
    public void testProject(Project project) {
        LOG.info("test project:" + project);
        String projectPath = project.getPath();
        File launchFile = new File(projectPath + File.separator + "fake_move_base_amcl.launch");
        //检测launch文件是否存在，不存在不能启动测试
        if (!launchFile.exists()){
            LOG.error("no fake_move_base_amcl.launch file in "+project);
            return;
        }
        //将rbx1的launch文件替换为项目自带的launch文件
        try {
            FileUtils.forceDelete(new File(growningAiConfig.getUploadPath()+File.separator+
                        "rbx1/rbx1_nav/launch/fake_move_base_amcl.launch"));
            FileUtils.copyFileToDirectory(launchFile,new File(growningAiConfig.getUploadPath()+File.separator+
                    "rbx1/rbx1_nav/launch"));
        } catch (IOException e) {
            LOG.error("切换launch文件失败:"+e.getMessage());
            return;
        }
        //保存项目id信息
        try {
            FileUtils.writeStringToFile(new File(growningAiConfig.getUploadPath()+File.separator+"rbx1/project_id.txt"),
                    project.getId()+"","utf-8");
        } catch (IOException e) {
            LOG.error("写入project id 失败："+e.getMessage());
            return;
        }
        //编译所有node
        String compileCommand="source /opt/ros/kinetic/setup.bash && source /root/WorkSpaces/catkin_ws/devel/setup.sh && catkin_make";
        if(LinuxCmdUtils.executeLinuxCmdWithPath(compileCommand,growningAiConfig.getCatkinPath())){
            LOG.info("编译成功");
            //执行测试命令
            LinuxCmdUtils.executeLinuxCmdWithPath("source /opt/ros/kinetic/setup.bash && source /root/WorkSpaces/catkin_ws/devel/setup.sh && sh /root/WorkSpaces/shell.sh",growningAiConfig.getCatkinPath());
            LOG.info("运行测试命令成功");
            return;
        }else {
            LOG.error("编译出错："+project);
            return;
        }
    }
}
