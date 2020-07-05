package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Announce;
import com.peter.mapper.AnnounceMapper;
import com.peter.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/6/21 1:17
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnounceMapper announceMapper;
    @Override
    public PageInfo<Announce> getAnnouncements(int pc, int ps) {
        PageHelper.startPage(pc,ps);
        List<Announce> announces = announceMapper.selectByExample(null);
        PageInfo<Announce> announcePageInfo = new PageInfo<>(announces);
//        announcePageInfo.setList(announces);
        return announcePageInfo;
    }
}
