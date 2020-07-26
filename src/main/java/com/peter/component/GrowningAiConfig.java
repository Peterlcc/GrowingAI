package com.peter.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "system.growingai")
public class GrowningAiConfig {
    //上传路径
    private String uploadPath;
    //删除的临时目录
    private String tmpDir;
    private String catkinPath;
    private String datasetRoot;

    private String webvizAddr;

    public String getWebvizAddr() {
        return webvizAddr;
    }

    public void setWebvizAddr(String webvizAddr) {
        this.webvizAddr = webvizAddr;
    }

    public String getDatasetRoot() {
        return datasetRoot;
    }

    public void setDatasetRoot(String datasetRoot) {
        this.datasetRoot = datasetRoot;
    }

    public String getCatkinPath() {
        return catkinPath;
    }
    public String getCatkinSrcPath() {
        return catkinPath+File.separator+"src";
    }

    public void setCatkinPath(String catkinPath) {
        this.catkinPath = catkinPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }
}