package com.peter.service;

import com.peter.bean.Project;

import java.util.List;

public interface TaskUploadService {
    void upload(Project project);
    int getProjectsInTask();
}
