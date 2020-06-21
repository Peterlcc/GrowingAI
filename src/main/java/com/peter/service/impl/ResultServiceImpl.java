package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Result;
import com.peter.bean.ResultExample;
import com.peter.mapper.ResultMapper;
import com.peter.service.ResultService;
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

    @Override
    public PageInfo<Result> getResults(int pc, int ps) {
        PageHelper.startPage(pc,ps);
        List<Result> results = resultMapper.selectByExample(null);
        PageInfo<Result> resultPageInfo = new PageInfo<>();
        resultPageInfo.setList(results);
        return resultPageInfo;
    }

    @Override
    public boolean save(Result result) {
        if (result.getProjectId()==null){
            LOG.error("project id of result to save is null!");
            return false;
        }
        ResultExample resultExample = new ResultExample();
        ResultExample.Criteria criteria = resultExample.createCriteria();
        criteria.andProjectIdEqualTo(result.getProjectId());
        List<Result> results = resultMapper.selectByExample(resultExample);
        if (results!=null&&results.size()==1){
            result.setId(results.get(0).getId());
            resultMapper.updateByPrimaryKey(result);
            return  true;
        }else if (results!=null&&results.size()>1){
            LOG.error("failed to save result,because we found "+results.size()+" results with project id ="+result.getProjectId());
            return false;
        }else{
            resultMapper.insertSelective(result);
            LOG.info("result:"+result+" is saved successfully!");
            return  true;
        }
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
}
