package com.peter.component;

import com.peter.bean.Project;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class ProjectTaskQueue {
    private Log LOG = LogFactory.getLog(ProjectTaskQueue.class);
    private ArrayBlockingQueue<Project> tasks=new ArrayBlockingQueue<Project>(100);
    public Project getTask(){
        try {
            return tasks.size()==0?null:tasks.take();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void addTask(Project project){
        try {
            tasks.put(project);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public int getTaskSize(){
        return tasks.size();
    }
    public boolean isEmpty(){
        return tasks.isEmpty();
    }
}
