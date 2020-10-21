package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    void save(Project project);
    Project getProjectByIdWithUser(Integer id);
    PageInfo<Project> getProjectsByUserId(int pc, int ps,Integer userId);
    PageInfo<Project> getProjects(int pc, int ps);
    List<Map<String,Object>> getStructById(Integer id);
    Project getProjectDetail(Integer id);
    List<Project> getAllSimpleProjects();

    Project getProjectById(Integer id);
    boolean update(Project project);
    boolean delete(Integer id);

    Map<String,List> spiderAnalyze();
}
