package com.peter.component;

import com.peter.bean.Project;
import com.peter.service.TestService;
import com.peter.utils.LinuxCmdUtils;
import com.peter.utils.RunTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TestTask {
    private final static Log LOG = LogFactory.getLog(TestTask.class);

    private int runCount=0;

    @Autowired
    private TestService testService;

    @Autowired
    private ProjectTaskQueue projectTaskQueue;

    @Autowired
    private RunTag runTag;

    @Autowired
    private GrowningAiConfig growningAiConfig;
    /**
     * 0 * * * * * 每分钟
     * 0 0 * * * * 每小时
     */
    @Scheduled(cron ="${scheduler.cron}")
    public void testProject() {
        if (runTag.getRunFlag()){
            LOG.info("some task is running!");
            runCount++;
            if (runCount>=growningAiConfig.getRunSum()){
                LOG.info("the test task running to long ,need tobe killed!");
                projectTaskQueue.pop();
                LinuxCmdUtils.killShell();
            }
            return;
        }
        Project project = projectTaskQueue.getTask();
        if (project==null) {
            LOG.info("queue is empty!");
            return;
        }
        LOG.info("---------TestTask running :project:"+project);
        runTag.setRunFlag(true);
        runCount=0;
        if (project.getTypeId()==1) {
            testService.testProject(project);
        }else{
            testService.testProjectWithDatasets(project);
        }
        LOG.info("---------TestTask finished!");
    }
}
