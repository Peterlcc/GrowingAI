package com.peter.service.impl;

import com.peter.bean.Project;
import com.peter.component.ProjectTaskQueue;
import com.peter.service.TaskUploadService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskUploadServiceImpl implements TaskUploadService {
    private Log LOG = LogFactory.getLog(TaskUploadServiceImpl.class);
    @Autowired
    private ProjectTaskQueue projectTaskQueue;
    @Override
    @Async
    public void upload(Project project) {
        LOG.info("uploading project:"+project+" to blocking queue.");
        if (projectTaskQueue.isFull()){
            LOG.info("task queue is full,method will be blocked with project:"+project);
        }
        projectTaskQueue.addTask(project);
        LOG.info("task uploaded with project:"+project);
    }

    @Override
    public int getProjectsInTask() {
        return projectTaskQueue.getRemaining();
    }

}
