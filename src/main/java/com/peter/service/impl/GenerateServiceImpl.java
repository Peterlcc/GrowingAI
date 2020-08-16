package com.peter.service.impl;

import com.peter.bean.File;
import com.peter.bean.FileType;
import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.dom.XML_Reader;
import com.peter.service.FileService;
import com.peter.service.FileTypeService;
import com.peter.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lcc
 * @date 2020/8/9 20:26
 */
@Service
public class GenerateServiceImpl implements GeneratorService {
    private static final int TYPE_ID=3;

    @Autowired
    private GrowningAiConfig growningAiConfig;
    @Autowired
    private FileTypeService fileTypeService;

    /**
     * 根据输入的xml文件对象，返回生成的cpp的文件对象描述
     * @param file
     * @return
     */
    @Override
    public File generate(File file, User user) {
        FileType projectType = fileTypeService.getById(TYPE_ID);
        XML_Reader.xmlReader(file.getPath(),growningAiConfig.getLibPath(),String.join(java.io.File.separator,growningAiConfig.getFilesDir(),user.getNumber(),projectType.getName()),file.getName());

        File pro = new File();

        pro.setUserId(file.getUserId());
        pro.setName(file.getName());
        pro.setTypeId(TYPE_ID);
        pro.setCreatetime(new Date());
        pro.setPath(String.join(java.io.File.separator,growningAiConfig.getFilesDir(),user.getNumber(),projectType.getName(),file.getName()));

        return pro;
    }
}
