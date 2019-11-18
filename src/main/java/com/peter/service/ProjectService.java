package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.bean.Project;

public interface ProjectService {
    void save(Project project);
    Project getProjectById(Integer id);
    Project getProjectByName(String name);
    PageInfo<Project> getProjects(int pc, int ps);

    void delete(Integer id);
}
