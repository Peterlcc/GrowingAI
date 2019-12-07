package com.peter.service.impl;

import com.peter.bean.Project;
import com.peter.bean.Result;
import com.peter.component.GrowningAiConfig;
import com.peter.service.TestService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    private Log LOG = LogFactory.getLog(TestServiceImpl.class);
    @Autowired
    private GrowningAiConfig growningAiConfig;

    @Override
    public Result testProject(Project project) {
        LOG.info("test:project=" + project);
        return new Result(true, "100分");
    }
}
