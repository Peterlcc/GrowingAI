package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.*;
import com.peter.component.GrowningAiConfig;
import com.peter.mapper.ProjectMapper;
import com.peter.mapper.ResultMapper;
import com.peter.mapper.ScoreMapper;
import com.peter.mapper.UserMapper;
import com.peter.service.ProjectService;
import com.peter.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {
    private Log LOG = LogFactory.getLog(ProjectServiceImpl.class);
    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private GrowningAiConfig growningAiConfig;

    private File deleteDir = null;

    @Override
    public void save(Project project) {
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(project.getName());
        List<Project> list = projectMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            LOG.info("project to save is not existed! then insert");
            projectMapper.insertSelective(project);
        } else {
            LOG.info("project to save is existed! then update");
            projectMapper.updateByPrimaryKey(list.get(0));
        }
    }

    @Override
    public Project getProjectById(Integer id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    @Override
    public Project getProjectByIdWithUser(Integer id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project==null) {
            LOG.info("get project by id:" + id + " is not existed! return null");
        }
        project.setUser(userMapper.selectByPrimaryKey(project.getUserId()));
        LOG.info("get project by id:" + id + " with user:"+project.getUser());
        return project;
    }

    @Override
    public PageInfo<Project> getProjectsByUserId(int pc, int ps, Integer userId) {
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        PageHelper.startPage(pc, ps);
        List<Project> projects = projectMapper.selectByExample(example);
        projects.stream().forEach(project -> project.setUser(userMapper.selectByPrimaryKey(project.getUserId())));
        LOG.info("getProjectsByUserId pc:" + pc + " ps:" + ps + " size:" + projects.size());
        PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
        if (projects == null || projects.size() == 0) {
            LOG.info("getProjectByUserId,no project of user :" + userId);
        }
        return pageInfo;
    }

    @Override
    public PageInfo<Project> getProjects(int pc, int ps) {
        PageHelper.startPage(pc, ps);
        List<Project> projects = projectMapper.selectByExample(null);
        LOG.info("getProjects pc:" + pc + " ps:" + ps + " size:" + projects.size());
        projects.stream().forEach(project -> project.setUser(userMapper.selectByPrimaryKey(project.getUserId())));
        PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> getStructById(Integer id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project == null) {
            LOG.info("getStructById selectByPrimaryKey is null!");
            return null;
        }
        List<Map<String, Object>> struct = FileUtil.getPathStruct(project.getPath());
        LOG.info("getStructById project struct is:" + struct);
        return struct;
    }

    @Override
    public void delete(Integer id) {
        if (deleteDir == null) {
            deleteDir = new File(growningAiConfig.getTmpDir());
        }
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project == null) {
            LOG.info("can't find project by id:" + id + " delete failed!");
            return;
        }
        String name = project.getName();
        String path = project.getPath();
        File src = new File(path);
        try {
            FileUtils.copyDirectoryToDirectory(src, deleteDir);
            FileUtils.deleteDirectory(new File(path));
            LOG.info("project " + name + " in path:" + path + " is deleted");
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        LOG.info("project " + name + " is deleted");
        projectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Project getProjectDetail(Integer id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project == null) return null;
        ResultExample resultExample = new ResultExample();
        ResultExample.Criteria criteria = resultExample.createCriteria();
        criteria.andProjectIdEqualTo(project.getId());
        List<Result> results = resultMapper.selectByExample(resultExample);
        if (results == null || results.size() == 0) project.setResults(null);
        else project.setResults(results);
        return project;
    }


}
