package com.peter.component;

import com.peter.bean.Project;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lcc
 * @date 2020/10/13 下午3:36
 */
public abstract class TaskQueue {
    protected static final Log LOG = LogFactory.getLog(TaskQueue.class);

    public abstract Project getTask();
    public abstract long getTaskSize();
    public abstract long getRemaining();
    public abstract boolean isEmpty();
    public abstract boolean isFull();

    public abstract void addTask(Project project,boolean again);

}
