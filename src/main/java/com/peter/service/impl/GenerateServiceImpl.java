package com.peter.service.impl;

import com.peter.bean.File;
import com.peter.bean.FileType;
import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.dom.GenNavigation;
import com.peter.dom.XML_Reader;
import com.peter.dom.modes;
import com.peter.service.FileService;
import com.peter.service.FileTypeService;
import com.peter.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/8/9 20:26
 */
@Service
@Slf4j
public class GenerateServiceImpl implements GeneratorService {
    private static final int TYPE_ID = 3;

    @Autowired
    private GrowningAiConfig growningAiConfig;
    @Autowired
    private FileTypeService fileTypeService;

    /**
     * 根据输入的xml文件对象，返回生成的cpp的文件对象描述
     *
     * @param file
     * @return
     */
    @Override
    public File generate(File file, User user, Map<String, String> params) {
        FileType projectType = fileTypeService.getById(TYPE_ID);
//        XML_Reader.xmlReader(file.getPath(),growningAiConfig.getLibPath(),
//                String.join(java.io.File.separator,growningAiConfig.getFilesDir(),user.getNumber(),projectType
//                .getName()),
//                file.getName());
        List<modes> modes = XML_Reader.xmlReader(file.getPath());
        String genPath = String.join(java.io.File.separator, growningAiConfig.getFilesDir(), user.getNumber(),
                projectType.getName(), file.getName());
        GenNavigation genNavigation = new GenNavigation(modes, growningAiConfig.getLibPath(), genPath);

        try {
            genNavigation.gen(params.get("env"), params.get("require"));
        } catch (IOException e) {
            log.error("根据文件" + file.getName() + "生成项目出错:" + e.getMessage());
            e.printStackTrace();
        }
        File pro = new File();

        pro.setUserId(file.getUserId());
        pro.setName(file.getName());
        pro.setTypeId(TYPE_ID);
        pro.setCreatetime(new Date());
        pro.setPath(genPath);

        return pro;
    }
}
