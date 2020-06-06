package com.peter.service.impl;

import com.peter.bean.SysModel;
import com.peter.bean.SysModelExample;
import com.peter.mapper.SysModelMapper;
import com.peter.service.SysModelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/5/17 13:41
 */
@Service
public class SysModelServiceImpl implements SysModelService {
    private Log LOG = LogFactory.getLog(SysModelServiceImpl.class);

    @Autowired
    private SysModelMapper sysModelMapper;

    @Override
    public SysModel getModelById(Integer id) {
        SysModel sysModel = sysModelMapper.selectByPrimaryKey(id);
        return sysModel;
    }

    @Override
    public List<SysModel> getAll() {
        List<SysModel> sysModels = sysModelMapper.selectByExample(null);
        return sysModels;
    }

    @Override
    public void save(SysModel model) {
        SysModelExample sysModelExample = new SysModelExample();
        SysModelExample.Criteria criteria = sysModelExample.createCriteria();
        criteria.andNameEqualTo(model.getName());
        List<SysModel> sysModels = sysModelMapper.selectByExample(sysModelExample);
        if (sysModels == null || sysModels.size() == 0) {
            sysModelMapper.insertSelective(model);
        } else {
            model.setId(sysModels.get(0).getId());
            sysModelMapper.updateByPrimaryKey(model);
        }
    }
}
