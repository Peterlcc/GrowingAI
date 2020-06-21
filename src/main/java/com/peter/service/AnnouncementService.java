package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Announce;

/**
 * @author lcc
 * @date 2020/6/21 1:16
 */
public interface AnnouncementService {
    PageInfo<Announce> getAnnouncements(int pc,int ps);
}
