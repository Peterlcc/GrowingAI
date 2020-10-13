package com.peter.component;

import com.peter.bean.Project;
import com.peter.bean.RedisProject;

import java.util.Arrays;
import java.util.List;

/**
 * @author lcc
 * @date 2020/10/13 下午3:51
 */
public class MultiLevelTaskQueue extends TaskQueue {
    private static final List<String> taskKeys= Arrays.asList("task_queue","task_queue_retry");

    private int n=taskKeys.size();
    private int index=0;

    private RedisUtil redisUtil=null;

    public MultiLevelTaskQueue(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private void updateIndex(){
        index=(index+1)%n;
    }

    @Override
    public Project getTask() {
        Project project=null;
        for (int i = 0; i < n; i++) {
            if (getTaskSize()!=0){
                project = (Project) redisUtil.lpop(taskKeys.get(index), 0);
                updateIndex();
                break;
            }
            updateIndex();
        }
        return project;
    }
    @Override
    public void addTask(Project project,boolean again){
        RedisProject redisProject = new RedisProject(project, again);
        if (again){
            redisUtil.rpush(taskKeys.get(1),redisProject);
        }else{
            redisUtil.rpush(taskKeys.get(0),redisProject);
        }
    }

    @Override
    public long getTaskSize() {
        return redisUtil.lGetListSize(taskKeys.get(index));
    }

    @Override
    public long getRemaining() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
