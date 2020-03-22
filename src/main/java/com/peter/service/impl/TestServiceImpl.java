package com.peter.service.impl;

import com.peter.bean.Dataset;
import com.peter.bean.Project;
import com.peter.bean.Result;
import com.peter.component.GrowningAiConfig;
import com.peter.mapper.DatasetMapper;
import com.peter.service.TestService;
import com.peter.utils.FileUtil;
import com.peter.utils.LinuxCmdUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private Log LOG = LogFactory.getLog(TestServiceImpl.class);
    @Autowired
    private GrowningAiConfig growningAiConfig;
    @Autowired
    private DatasetMapper datasetMapper;

    @Override
    @Async
    public void testProjectWithDatasets(Project project) {
        List<Dataset> datasets = datasetMapper.selectByExample(null);
        for (Dataset dataset:datasets){
//            switchDataset();//切换数据集
            testWithDataset(project,dataset);
        }
    }

    private void testWithDataset(Project project, Dataset dataset) {
        String path = dataset.getPath();
        LOG.info("test project with dataset:" + project);
        if (!prepareForTest(project,dataset)){
            LOG.error("测试项目"+project.getId()+"准备失败");
            return;
        }
        //编译node
        String compileCommand="source /opt/ros/kinetic/setup.bash && source /root/WorkSpaces/catkin_ws/devel/setup.sh && catkin_make";
        if(LinuxCmdUtils.executeLinuxCmdWithPath(compileCommand,growningAiConfig.getCatkinPath())){
            LOG.info("编译成功");
            //执行测试命令
            LinuxCmdUtils.executeLinuxCmdWithPath("source /opt/ros/kinetic/setup.bash && source /root/WorkSpaces/catkin_ws/devel/setup.sh && sh "+path+File.separator+"shell.sh",growningAiConfig.getCatkinPath());
            LOG.info("运行测试命令成功");
            return;
        }else {
            LOG.error("编译出错："+project);
            return;
        }
    }

    private void switchDataset() {

    }

    @Override
    @Async
    public void testProject(Project project) {
        LOG.info("test project:" + project);
        if (!prepareForTest(project,null)){
            LOG.error("测试项目"+project.getId()+"准备失败");
            return;
        }

        //编译node
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

    private boolean prepareForTest(Project project,Dataset dataset) {
        String projectPath = project.getPath();
        File projectIdFile = new File(growningAiConfig.getUploadPath() + File.separator + "rbx1/project_id.txt");
        if (projectIdFile.exists())
        {
            try {
                int id = Integer.parseInt(FileUtils.readFileToString(projectIdFile, "utf-8"));
                if (project.getId().intValue()==id){
                    return true;
                }
            } catch (IOException e) {
                LOG.error("项目id文件读取失败："+project);
            }
        }


        File launchFile = new File(projectPath + File.separator + "fake_move_base_amcl.launch");
        //检测launch文件是否存在，不存在不能启动测试
        if (!launchFile.exists()){
            LOG.error("no fake_move_base_amcl.launch file in "+project);
            return false;
        }
        //将rbx1的launch文件替换为项目自带的launch文件
        try {
            FileUtils.forceDelete(new File(growningAiConfig.getUploadPath()+File.separator+
                        "rbx1/rbx1_nav/launch/fake_move_base_amcl.launch"));
            FileUtils.copyFileToDirectory(launchFile,new File(growningAiConfig.getUploadPath()+File.separator+
                    "rbx1/rbx1_nav/launch"));
        } catch (IOException e) {
            LOG.error("切换launch文件失败:"+e.getMessage());
            return false;
        }
        //保存项目id信息
        try {
            FileUtils.writeStringToFile(new File(growningAiConfig.getUploadPath()+File.separator+"rbx1/project_id.txt"),
                    project.getId()+"","utf-8");
        } catch (IOException e) {
            LOG.error("写入project id 失败："+e.getMessage());
            return false;
        }
        if (dataset!=null)
        {
            //保存dataset信息
            try {
                FileUtils.writeStringToFile(new File(growningAiConfig.getUploadPath()+File.separator+"rbx1/dataset_id.txt"),
                        dataset.getId()+"","utf-8");
            } catch (IOException e) {
                LOG.error("写入dataset id 失败："+e.getMessage());
                return false;
            }
        }

        //删除原来测试的项目
        String pid="/root/growningai/pid.txt";
        File pidFile=new File(pid);
        try {
            String tmpPath = FileUtils.readFileToString(pidFile,"utf-8");
            FileUtils.forceDelete(new File(tmpPath));
            FileUtils.forceDelete(pidFile);
        } catch (IOException e) {
            LOG.error("删除之前的测试项目失败："+e.getMessage());
            return false;
        }


        //将项目复制到rossrc下
        try {
            FileUtils.copyDirectoryToDirectory(new File(projectPath),new File(growningAiConfig.getCatkinSrcPath()));
            //测试的项目路径保存，方便下次测试清除
            FileUtils.writeStringToFile(pidFile,project.getPath(),"utf-8");
        } catch (IOException e) {
            LOG.error("将项目"+project.getId()+"复制到rossrc下失败："+e.getMessage());
            return false;
        }
        return true;
    }
}
