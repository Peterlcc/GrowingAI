package com.peter.service.impl;

import com.peter.bean.*;
import com.peter.component.GrowningAiConfig;
import com.peter.component.TaskQueue;
import com.peter.mapper.DatasetMapper;
import com.peter.service.ResultService;
import com.peter.service.TestService;
import com.peter.utils.LinuxCmdUtils;
import com.peter.utils.RunTag;
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

    @Autowired
    private ResultService resultService;

    @Autowired
    private TaskQueue taskQueue;

    @Autowired
    private RunTag runTag;

    @Override
    @Async
    public void testProjectWithDatasets(Project project) {
        DatasetExample datasetExample = new DatasetExample();
        DatasetExample.Criteria criteria = datasetExample.createCriteria();
        criteria.andTypeidEqualTo(project.getTypeId());
        List<Dataset> datasets = datasetMapper.selectByExample(datasetExample);
        LOG.info("get datasets with type "+project.getTypeId()+": "+datasets);
        for (Dataset dataset:datasets){
//            switchDataset();//切换数据集
            testWithDataset(project,dataset);
        }
        runTag.setRunFlag(false);
    }

    private void testWithDataset(Project project, Dataset dataset) {
        String path = dataset.getPath();
        LOG.info("test project with dataset:" + project);
        boolean resultPrepare = prepareForTest(project, dataset);
        Result error=new Result();
        error.setDatasetId(dataset.getId());
        error.setLength(Double.MAX_VALUE);
        error.setPoints(0);
        error.setTime(Double.MAX_VALUE);
        error.setProjectId(project.getId());
        if (!resultPrepare){
            LOG.error("测试项目"+project.getId()+"准备失败");
            //TODO 设置错误信息
            resultService.save(error);
            return;
        }
        //编译node
        String compileCommand="source /opt/ros/kinetic/setup.bash && source "+growningAiConfig.getCatkinPath()+"/devel/setup.sh && catkin_make";
        if(LinuxCmdUtils.executeLinuxCmdWithPath(compileCommand,growningAiConfig.getCatkinPath())){
            LOG.info("编译成功");
            //执行测试命令
            LinuxCmdUtils.executeLinuxCmdWithPath("source /opt/ros/kinetic/setup.bash && source "+growningAiConfig.getCatkinPath()+"/devel/setup.sh && sh "+path+File.separator+"shell.sh",growningAiConfig.getCatkinPath());
            LOG.info("运行测试命令成功");
            return;
        }else {
            LOG.error("编译出错："+project);
            //TODO 设置错误信息
            resultService.save(error);
            return;
        }
    }

    private void switchDataset() {

    }

    @Override
    @Async
    public void testProject(Project project) {
        Result error=new Result();
        error.setDatasetId(1);
        error.setLength(Double.MAX_VALUE);
        error.setPoints(0);
        error.setTime(Double.MAX_VALUE);
        error.setProjectId(project.getId());
        LOG.info("test project:" + project);
        boolean resultPrepare = prepareForTest(project, null);
        if (!resultPrepare){
            LOG.error("测试项目"+project.getId()+"准备失败");
            //TODO 设置错误信息
            resultService.save(error);
            runTag.setRunFlag(false);
            return;
        }

        //编译node
        String compileCommand="source /opt/ros/kinetic/setup.bash && source "+growningAiConfig.getCatkinPath()+"/devel/setup.sh && chmod -R 777 "+growningAiConfig.getCatkinPath()+"/src && catkin_make";
        if(LinuxCmdUtils.executeLinuxCmdWithPath(compileCommand,growningAiConfig.getCatkinPath())){
            LOG.info("编译成功");
            //执行测试命令
//            LinuxCmdUtils.executeLinuxCmdWithPath("source /opt/ros/kinetic/setup.bash && source /home/peter/WorkSpaces/catkin_ws/devel/setup.sh && sh /home/peter/AppStore/GrowningAI/scripts/shell.sh",growningAiConfig.getCatkinPath());
            LinuxCmdUtils.executeLinuxCmdWithPath("source /opt/ros/kinetic/setup.bash && source "+growningAiConfig.getCatkinPath()+"/devel/setup.sh && sh "+growningAiConfig.getScriptsPath()+File.separator+growningAiConfig.getStartScript(),growningAiConfig.getCatkinPath());
            LOG.info("运行测试命令成功");
            runTag.setRunFlag(false);
            return;
        }else {
            LOG.error("编译出错："+project);
            //TODO 设置错误信息
            resultService.save(error);
            runTag.setRunFlag(false);
            return;
        }
    }

    private boolean prepareForTest(Project project,Dataset dataset) {
        String projectPath = project.getPath();
        File launchFile = new File(projectPath + File.separator + "fake_move_base_amcl.launch");
        //检测launch文件是否存在，不存在不能启动测试
        if (!launchFile.exists()){
            LOG.error("no fake_move_base_amcl.launch file in "+project);
            return false;
        }
        //将rbx1的launch文件替换为项目自带的launch文件
        try {
            FileUtils.forceDelete(new File(growningAiConfig.getCatkinSrcPath()+File.separator+
                        "rbx1/rbx1_nav/launch/fake_move_base_amcl.launch"));
            FileUtils.copyFileToDirectory(launchFile,new File(growningAiConfig.getCatkinSrcPath()+File.separator+
                    "rbx1/rbx1_nav/launch"));
        } catch (IOException e) {
            LOG.error("切换launch文件失败:"+e.getMessage());
            return false;
        }
        //保存项目id信息
        try {
            FileUtils.writeStringToFile(new File(growningAiConfig.getPidPath()),
                    project.getId()+"","utf-8");
            LOG.info("write pid "+project.getId() +" to "+growningAiConfig.getPidPath());
        } catch (IOException e) {
            LOG.error("写入project id 失败："+e.getMessage());
            return false;
        }
        //保存dataset信息
        try {
            int did=0;
            if (dataset!=null)
            {
                did=dataset.getId();
            }
            FileUtils.writeStringToFile(new File(growningAiConfig.getDatasetIdPath()),
                    did+"","utf-8");
            LOG.info("write dataset id completed!");
        } catch (IOException e) {
            LOG.error("写入dataset id 失败："+e.getMessage());
//            return false;
        }

        //删除原来测试的项目
//        String pid="/home/peter/AppStore/GrowningAI/pid.txt";
        String plocPath = growningAiConfig.getPlocPath();
        File plocFile=new File(plocPath);
        try {
            String tmpPath = FileUtils.readFileToString(plocFile,"utf-8");
            FileUtils.forceDelete(new File(tmpPath));
            FileUtils.forceDelete(plocFile);
            LOG.info("delete project in "+tmpPath);
        } catch (IOException e) {
            LOG.error("删除之前的测试项目失败："+e.getMessage());
//            return false;
        }


        //将项目复制到rossrc下
        try {
            FileUtils.copyDirectoryToDirectory(new File(projectPath),new File(growningAiConfig.getCatkinSrcPath()));
            //测试的项目路径保存，方便下次测试清除
            FileUtils.writeStringToFile(plocFile,growningAiConfig.getCatkinSrcPath()+File.separator+new File(project.getPath()).getName(),"utf-8");
            LOG.info("copy project");
        } catch (IOException e) {
            LOG.error("将项目"+project.getId()+"复制到rossrc下失败："+e.getMessage());
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String pid="/home/peter/AppStore/GrowningAI/pid.txt";
        File pidFile=new File(pid);
        try {
            String s = FileUtils.readFileToString(pidFile, "utf-8");
            System.out.println(s);
            FileUtils.forceDelete(new File(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
