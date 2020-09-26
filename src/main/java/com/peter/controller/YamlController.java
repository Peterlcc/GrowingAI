package com.peter.controller;

import com.peter.component.GrowningAiConfig;
import com.peter.service.YamlService;
import com.peter.utils.FileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author lcc
 * @date 2020/9/26 12:44
 */
@RequestMapping("param")
@Controller
public class YamlController {
    private static final Log LOG= LogFactory.getLog(YamlController.class);

    @Autowired
    private GrowningAiConfig growningAiConfig;

    @Autowired
    private YamlService yamlService;

    @GetMapping("load")
    public String load(){
        return "forward:/param/yaml?name=default";
    }

    @GetMapping("yaml")
    public String getYaml(String name,Model model){
        File yamlDir = new File(growningAiConfig.getYamlDir());
        List<String> yamlNames=new ArrayList<>();
        if(!yamlDir.exists()){
            LOG.error("yaml dir not exist!");
        }else{
            File[] yamls = yamlDir.listFiles();
            for (File yaml : yamls) {
                String yamlName = FileUtil.getFileNameWithoutSuffix(yaml);
                yamlNames.add(yamlName);
            }
            LOG.info("load yamls from "+yamlDir.getAbsolutePath()+",["+String.join(",",yamlNames)+"]");
        }
        model.addAttribute("yamlNames",yamlNames);

        File file = new File(growningAiConfig.getYamlDir() + File.separator + name+".yaml");
        if(!file.exists()){
            LOG.error(file.getAbsolutePath()+" not exist!");
        }else{
            Map<String, Object> yaml = yamlService.read(file.getAbsolutePath());
            LOG.info("load yaml from "+file.getName());
            model.addAttribute("yamlNameText",name);
            model.addAttribute("yaml",yaml);
        }
        return "utils/yaml";
    }

    @PostMapping("save")
    @ResponseBody
    public String save(@RequestParam Map<String,Object> data){
        Map<String,Object> yaml=new HashMap<>();
        for (int i=0;i<data.size();i++){
            if (data.containsKey("name"+i)){
                yaml.put((String) data.get("name"+i),data.get("value"+i));
            }
        }
        String name = UUID.randomUUID().toString();
        String path=growningAiConfig.getTmpDir()+File.separator+"yamls"+File.separator+name+".yaml";
        yamlService.write(yaml,path);
        return name;
    }
    @GetMapping("download.yaml")
    public void download(String name, HttpServletResponse response){
        String path=growningAiConfig.getTmpDir()+File.separator+"yamls"+File.separator+name+".yaml";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(fileInputStream,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
