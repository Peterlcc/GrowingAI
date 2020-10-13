package com.peter.component;

import com.peter.bean.Project;

/**
 * @author lcc
 * @date 2020/10/13 下午3:44
 */
public class RedisTaskQueue extends TaskQueue {
    private static final String taskKey="task_queue";

    private RedisUtil redisUtil=null;

    public RedisTaskQueue(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public Project getTask() {
        if (getTaskSize()==0){
            return null;
        }
        Project project = (Project) redisUtil.lpop(taskKey, 0);
        return project;
    }

    @Override
    public long getTaskSize() {
        return redisUtil.lGetListSize(taskKey);
    }

    @Override
    public long getRemaining() {
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return getTaskSize()==0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void addTask(Project project, boolean again) {
        redisUtil.rpush(taskKey,project);
    }
}
