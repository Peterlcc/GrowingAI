package com.peter.service.impl;

import com.peter.bean.File;
import com.peter.dom.XML_Reader;
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
    private static final int TYPE_ID=2;

    /**
     * 根据输入的xml文件对象，返回生成的cpp的文件对象描述
     * @param file
     * @return
     */
    @Override
    public File generate(File file) {

        XML_Reader.xmlReader(file.getPath());

        File cpp = new File();

        cpp.setUserId(file.getUserId());
        cpp.setName(file.getName());
        cpp.setTypeId(TYPE_ID);
        cpp.setCreatetime(new Date());

        return cpp;
    }
}
