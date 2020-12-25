package com.peter.bean;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author lcc
 * @date 2020/10/13 下午5:27
 */
public class RedisProject extends Project{
    private int runNumber=0;

    public RedisProject() {
    }

    public RedisProject(int runNumber) {
        super();
        this.runNumber = runNumber;
    }
    public RedisProject(Project project,boolean again) {
        try {
            BeanUtils.copyProperties(this,project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (again){
            runNumber++;
        }
    }


    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    @Override
    public String toString() {
        return "RedisProject{" +
                "runNumber=" + runNumber +
                ",project="+super.toString()+
                '}';
    }
}
