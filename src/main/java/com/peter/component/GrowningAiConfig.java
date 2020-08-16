package com.peter.component;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "system.growingai")
@Data
@Accessors(chain = true)
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
    private String libPath;
    private Integer runSum;

    public String getCatkinSrcPath() {
        return catkinPath+File.separator+"src";
    }

}