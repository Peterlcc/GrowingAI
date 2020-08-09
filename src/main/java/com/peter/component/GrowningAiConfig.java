package com.peter.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "system.growingai")
public class GrowningAiConfig {
    //项目上传路径
    private String uploadPath;
    //xml文件上传路径
    private String filesDir;
    //删除的临时目录
    private String tmpDir;
    private String catkinPath;
    private String datasetRoot;


    //webviz address
    private String webvizAddr;

    //path of out.txt
    private String outPath;
    //path of project_id.txt
    private String pidPath;
    //path of dataset_id.txt
    private String datasetIdPath;
    //http url of admin login
    private String loginUrl;
    //http url of result to save
    private String saveUrl;
    //path of scripts in test
    private String scriptsPath;
    //name of script to start test
    private String startScript;

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public void setPidPath(String pidPath) {
        this.pidPath = pidPath;
    }

    public void setDatasetIdPath(String datasetIdPath) {
        this.datasetIdPath = datasetIdPath;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }

    public void setScriptsPath(String scriptsPath) {
        this.scriptsPath = scriptsPath;
    }

    public void setStartScript(String startScript) {
        this.startScript = startScript;
    }

    public String getDatasetIdPath() {
        return datasetIdPath;
    }

    public String getScriptsPath() {
        return scriptsPath;
    }

    public String getStartScript() {
        return startScript;
    }

    public String getOutPath() {
        return outPath;
    }

    public String getPidPath() {
        return pidPath;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getSaveUrl() {
        return saveUrl;
    }

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

    public String getFilesDir() {
        return filesDir;
    }

    public void setFilesDir(String filesDir) {
        this.filesDir = filesDir;
    }
}