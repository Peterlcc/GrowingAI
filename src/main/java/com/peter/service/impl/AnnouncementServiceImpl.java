package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Announce;
import com.peter.bean.AnnounceExample;
import com.peter.controller.AnnouncementController;
import com.peter.mapper.AnnounceMapper;
import com.peter.service.AnnouncementService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/6/21 1:17
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private Log LOG = LogFactory.getLog(AnnouncementController.class);

    @Autowired
    private AnnounceMapper announceMapper;
    @Override
    public PageInfo<Announce> getAnnouncements(int pc, int ps) {
        PageHelper.startPage(pc,ps);
        AnnounceExample example = new AnnounceExample();
        example.setOrderByClause("create_time desc");
        List<Announce> announces = announceMapper.selectByExample(example);
        LOG.info(announces);
        PageInfo<Announce> announcePageInfo = new PageInfo<>(announces);
        return announcePageInfo;
    }

    @Override
    public boolean save(Announce announce) {
        int i = announceMapper.insertSelective(announce);
        return i==1;
    }

    @Override
    public boolean update(Announce announce) {
        if (announce.getId()==null){
            LOG.error("the id ["+announce.getId()+"] of announce to update is null");
            return false;
        }
        Announce ann = announceMapper.selectByPrimaryKey(announce.getId());
        if (ann==null){
            LOG.error("the id ["+announce.getId()+"] of announce to update is not exist");
            return false;
        }
        int i = announceMapper.updateByPrimaryKey(announce);
        return i==1;
    }

    @Override
    public Announce get(Integer id) {
        Announce announce = announceMapper.selectByPrimaryKey(id);
        return announce;
    }

    @Override
    public boolean delete(Integer id) {
        int i = announceMapper.deleteByPrimaryKey(id);
        return i==1;
    }
}
