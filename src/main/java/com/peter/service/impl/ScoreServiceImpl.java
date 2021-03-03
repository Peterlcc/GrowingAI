package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Project;
import com.peter.bean.ProjectExample;
import com.peter.bean.Score;
import com.peter.bean.ScoreExample;
import com.peter.mapper.ProjectMapper;
import com.peter.mapper.ScoreMapper;
import com.peter.service.ProjectService;
import com.peter.service.ScoreService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScoreServiceImpl implements ScoreService {
    private Log LOG = LogFactory.getLog(ScoreServiceImpl.class);

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectService projectService;

    private static Date scoreStartDate=null;

    static {
        try {
            scoreStartDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageInfo<Map<String,Object>> getAdminOrderedScores(int pc, int ps) {
        ScoreExample scoreExample = new ScoreExample();
        scoreExample.setOrderByClause("score desc");
        PageHelper.startPage(pc, ps);
        List<Score> scores = scoreMapper.selectByExample(scoreExample);
        LOG.info("getProjectsWithScore pc:"+pc+" ps:"+ps+" size:"+scores.size());
        scores.stream().forEach(score -> score.setProject(projectService.getProjectByIdWithUser(score.getProjectId())));

        List<Map<String,Object>> results=new ArrayList<>();
        scores.stream().forEach(score -> {
            Map<String,Object> datas=new HashMap<>();
            datas.put("name",score.getProject().getName());
            datas.put("score",score.getScore());
            datas.put("author",score.getProject().getUser().getName());
            datas.put("date",score.getProject().getCreateTime());
            results.add(datas);
        });

        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);
        return pageInfo;
    }

    @Override
    public PageInfo<Score> getOrderedScores(int pc, int ps) {
        ScoreExample scoreExample = new ScoreExample();
        scoreExample.setOrderByClause("score asc");
        ScoreExample.Criteria criteria = scoreExample.createCriteria();

        ProjectExample projectExample = new ProjectExample();
        ProjectExample.Criteria projectExampleCriteria = projectExample.createCriteria();
        projectExampleCriteria.andCreateTimeGreaterThan(scoreStartDate);
        List<Project> projects = projectMapper.selectByExample(projectExample);
        List<Integer> ids=new ArrayList<>();
        for (Project project : projects) {
            ids.add(project.getId());
        }
        criteria.andProjectIdIn(ids);
        PageHelper.startPage(pc, ps);
        List<Score> scores = scoreMapper.selectByExample(scoreExample);
        LOG.info("getProjectsWithScore pc:"+pc+" ps:"+ps+" size:"+scores.size());
        scores.stream().forEach(score -> score.setProject(projectService.getProjectByIdWithUser(score.getProjectId())));

        PageInfo<Score> pageInfo = new PageInfo<Score>(scores);
        return pageInfo;
    }

    @Override
    public boolean add(Score score) {
        int i = scoreMapper.insertSelective(score);
        return i==1;
    }

    @Override
    public boolean update(Score score) {
        if (score.getId()==null){
            LOG.error("the id ["+score.getId()+"] of result to update is null");
            return false;
        }
        Score res = scoreMapper.selectByPrimaryKey(score.getId());
        if (res==null){
            LOG.error("the id ["+score.getId()+"] of result to update is not exist");
            return false;
        }
        int i = scoreMapper.updateByPrimaryKey(score);
        return i==1;
    }

    @Override
    public boolean delete(Integer id) {
        int i = scoreMapper.deleteByPrimaryKey(id);
        return i==1;
    }

    @Override
    public Score get(Integer id) {
        Score score = scoreMapper.selectByPrimaryKey(id);
        return score;
    }
}
