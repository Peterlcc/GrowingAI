package com.peter.component;

import com.peter.bean.Project;
import com.peter.service.TestService;
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
    private Log LOG = LogFactory.getLog(TestTask.class);

    @Autowired
    private TestService testService;

    @Autowired
    private ProjectTaskQueue projectTaskQueue;

    @Autowired
    private RunTag runTag;
    /**
     * 0 * * * * * 每分钟
     * 0 0 * * * * 每小时
     */
    @Scheduled(cron ="${scheduler.cron}")
    public void testProject() {
        if (runTag.getRunFlag()){
            LOG.info("some task is running!");
            return;
        }
        Project project = projectTaskQueue.getTask();
        if (project==null) {
            LOG.info("queue is empty!");
            return;
        }
        LOG.info("---------TestTask running :project:"+project);
        runTag.setRunFlag(true);
        if (project.getTypeId()==1) {
            testService.testProject(project);
        }else{
            testService.testProjectWithDatasets(project);
        }
        LOG.info("---------TestTask finished!");
    }
}
