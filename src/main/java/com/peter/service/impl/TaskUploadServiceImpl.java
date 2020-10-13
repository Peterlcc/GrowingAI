package com.peter.service.impl;

import com.peter.bean.Project;
import com.peter.component.TaskQueue;
import com.peter.service.TaskUploadService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TaskUploadServiceImpl implements TaskUploadService {
    private Log LOG = LogFactory.getLog(TaskUploadServiceImpl.class);
    @Autowired
    private TaskQueue taskQueue;
    @Override
    @Async
    public void upload(Project project) {
        LOG.info("uploading project:"+project+" to blocking queue.");
        if (taskQueue.isFull()){
            LOG.info("task queue is full,method will be blocked with project:"+project);
        }
        taskQueue.addTask(project,false);
        LOG.info("task uploaded with project:"+project);
    }
    @Override
    public long getProjectsInTask() {
        return taskQueue.getTaskSize();
    }
}
