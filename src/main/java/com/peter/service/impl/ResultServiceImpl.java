package com.peter.service.impl;

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
}
