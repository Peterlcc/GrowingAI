package com.peter.service;

import com.peter.bean.Project;

public interface TaskUploadService {
    void upload(Project project);
    long getProjectsInTask();
}
