package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Result;
import com.peter.bean.ResultExample;
import com.peter.component.ProjectTaskQueue;
import com.peter.component.TestTask;
import com.peter.mapper.ResultMapper;
import com.peter.service.ResultService;
import com.peter.service.TestService;
import com.peter.utils.RunTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    private Log LOG = LogFactory.getLog(ResultServiceImpl.class);
    @Autowired
    private ResultMapper resultMapper;
    @Autowired
    private ProjectTaskQueue projectTaskQueue;
    @Override
    public PageInfo<Result> getResults(int pc, int ps) {
        PageHelper.startPage(pc,ps);
        List<Result> results = resultMapper.selectByExample(null);
        PageInfo<Result> resultPageInfo = new PageInfo<>(results);
//        resultPageInfo.setList(results);
        return resultPageInfo;
    }

    @Override
    public boolean save(Result result) {
        projectTaskQueue.pop();
        if (result.getProjectId()==null){
            LOG.error("project id of result to save is null!");
            return false;
        }
        ResultExample resultExample = new ResultExample();
        ResultExample.Criteria criteria = resultExample.createCriteria();
        criteria.andProjectIdEqualTo(result.getProjectId());
        List<Result> results = resultMapper.selectByExample(resultExample);
        LOG.info("results:"+results);
        if (results!=null&&results.size()==1){
            result.setId(results.get(0).getId());
            resultMapper.updateByPrimaryKey(result);
            return  true;
        }else {
            LOG.error("failed to save result,because we found " + results.size() + " results with project id =" + result.getProjectId());
            return false;
        }
    }

    @Override
    public boolean add(Result result) {
        int i = resultMapper.insertSelective(result);
        return i==1;
    }

    @Override
    public boolean save(List<Result> res) {
        if (res==null||res.size()==0){
            LOG.info("提交的结果集合为空！");
            return false;
        }else if (res.size()!=3){
            LOG.info("提交的结果集合大小为"+res.size()+"不是三个！");
            return false;
        }else {
            projectTaskQueue.pop();
            ResultExample resultExample = new ResultExample();
            ResultExample.Criteria criteria = resultExample.createCriteria();
            int projectId = res.get(0).getProjectId();
            //检查结果的projectid是否相等
            for (Result r:res){
                if (r.getProjectId()!=projectId){
                    LOG.info("三个结果的projectid 不同！");
                    return false;
                }
            }
            criteria.andProjectIdEqualTo(projectId);
            List<Result> results = resultMapper.selectByExample(resultExample);
            if (results==null||results.size()==0){
                LOG.info("project which id is "+projectId+" has no results,then insert");
                res.stream().forEach(result -> {
                    resultMapper.insertSelective(result);
                });
            }else {
                LOG.info("project which id is "+projectId+" haven results"+results+" in tables,then delete first!");
                results.stream().forEach(result -> {
                    resultMapper.deleteByPrimaryKey(result.getId());
                });
                LOG.info("insert new results of project which id is "+projectId);
                res.stream().forEach(result -> {
                    resultMapper.insertSelective(result);
                });
            }
            return true;
        }
    }

    @Override
    public Result getResult(Integer id) {
        Result result = resultMapper.selectByPrimaryKey(id);
        return result;
    }

    @Override
    public boolean deleteResult(Integer id) {
        int i = resultMapper.deleteByPrimaryKey(id);
        return i==1;
    }

    @Override
    public boolean updateResult(Result result) {
        if (result.getId()==null){
            LOG.error("the id ["+result.getId()+"] of result to update is null");
            return false;
        }
        Result res = resultMapper.selectByPrimaryKey(result.getId());
        if (res==null){
            LOG.error("the id ["+result.getId()+"] of result to update is not exist");
            return false;
        }
        int i = resultMapper.updateByPrimaryKey(result);
        return i==1;
    }
}
