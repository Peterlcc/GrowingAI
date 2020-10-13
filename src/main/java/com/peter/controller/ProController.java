package com.peter.controller;

import com.github.pagehelper.PageInfo;
import com.peter.bean.File;
import com.peter.bean.FileType;
import com.peter.bean.User;
import com.peter.service.FileService;
import com.peter.service.FileTypeService;
import com.peter.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author lcc
 * @date 2020/8/16 9:54
 */
@Controller
@RequestMapping("pro")
public class ProController {
    private static final Log LOG = LogFactory.getLog(ProController.class);
    private static final int TYPE_ID = 3;
    private FileType fileType = null;

    @Autowired
    private FileService fileService;
    @Autowired
    private FileTypeService fileTypeService;

    @GetMapping("list")
    public String listPage(HttpServletRequest request, Model model) {
        String pcstr = request.getParameter("pc");
        if (pcstr == null || pcstr.equals("")) {
            pcstr = "1";
        }
        int pc = Integer.parseInt(pcstr);
        String psstr = request.getParameter("ps");
        if (psstr == null || psstr.equals("")) {
            psstr = "10";
        }
        int ps = Integer.parseInt(psstr);
        User user = (User) request.getSession().getAttribute("user");
        PageInfo<File> pageInfo = fileService.getFilesByUserId(pc, ps, user.getId(), TYPE_ID);
        LOG.info("list:" + pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        if (fileType == null) {
            fileType = fileTypeService.getById(TYPE_ID);
        }
        model.addAttribute("fileType", fileType);
        model.addAttribute("fileType", fileTypeService.getById(TYPE_ID));
        return "user/fileList";
    }

    @GetMapping("download/{id}")
    @ResponseBody
    public String download(@PathVariable("id") Integer id, HttpServletResponse response) {

        File pro = fileService.getById(id);
        java.io.File tmp = new java.io.File("tmp");
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        FileUtil.folder2zip(pro.getPath(), "tmp", pro.getName() + ".zip");
        FileInputStream inputStream = null;
        try {
            String zipFileName = "tmp" + java.io.File.separator + pro.getName() + ".zip";
            inputStream = new FileInputStream(zipFileName);
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return "downloaded";
    }
}
