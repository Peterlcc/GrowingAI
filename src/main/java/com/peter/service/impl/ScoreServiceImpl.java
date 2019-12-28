package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Score;
import com.peter.bean.ScoreExample;
import com.peter.mapper.ScoreMapper;
import com.peter.service.ProjectService;
import com.peter.service.ScoreService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    private Log LOG = LogFactory.getLog(ScoreServiceImpl.class);

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ProjectService projectService;

    @Override
    public PageInfo<Score> getOrderedScores(int pc, int ps) {
        ScoreExample scoreExample = new ScoreExample();
        scoreExample.setOrderByClause("score desc");
        PageHelper.startPage(pc, ps);
        List<Score> scores = scoreMapper.selectByExample(scoreExample);
        LOG.info("getProjectsWithScore pc:"+pc+" ps:"+ps+" size:"+scores.size());
        scores.stream().forEach(score -> score.setProject(projectService.getProjectByIdWithUser(score.getProjectId())));
        PageInfo<Score> pageInfo = new PageInfo<Score>(scores);
        return pageInfo;
    }
}
