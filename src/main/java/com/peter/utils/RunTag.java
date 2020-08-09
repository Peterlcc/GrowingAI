package com.peter.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lcc
 * @date 2020/8/9 上午10:39
 */
public class RunTag {
    private AtomicBoolean runFlag=new AtomicBoolean(false);
    public void setRunFlag(boolean runFlag) {
        this.runFlag.set(runFlag);
    }

    public boolean getRunFlag() {
        return runFlag.get();
    }
}
