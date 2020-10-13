package com.peter.component;

import com.peter.bean.Project;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author lcc
 * @date 2020/10/13 下午3:41
 */
public class MemeoryTaskQueue extends TaskQueue{
    private static final long capacity=30;
    private ArrayBlockingQueue<Project> tasks=new ArrayBlockingQueue<Project>(30);
    @Override
    public Project getTask() {
        try {
            return tasks.size()==0?null:tasks.take();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
//        return tasks.size()==0?null:tasks.peek();
    }

    @Override
    public void addTask(Project project, boolean again) {
        try {
            tasks.put(project);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getTaskSize() {
        return tasks.size();
    }

    @Override
    public long getRemaining(){
        return tasks.remainingCapacity();
    }
    @Override
    public boolean isFull(){
        return tasks.remainingCapacity()==0;
    }

    @Override
    public boolean isEmpty(){
        return tasks.isEmpty();
    }
}
