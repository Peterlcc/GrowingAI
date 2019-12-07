package com.peter.component;

import com.peter.bean.Project;
import com.peter.bean.Result;
import com.peter.service.TestService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {
    private Log LOG = LogFactory.getLog(TestTask.class);

    @Autowired
    private TestService testService;

    @Autowired
    private ProjectTaskQueue projectTaskQueue;

    /**
     * 0 * * * * * 每分钟
     * 0 0 * * * * 每小时
     */
    @Scheduled(cron ="${system.growingai.cron}")
    public void testProject() {
        Project project = projectTaskQueue.getTask();
        if (project==null) {
            LOG.info("queue is empty!");
            return;
        }
        LOG.info("---------TestTask running :project:"+project);
        Result result = testService.testProject(project);
        LOG.info("---------TestTask finished :result:"+result);
    }
}
