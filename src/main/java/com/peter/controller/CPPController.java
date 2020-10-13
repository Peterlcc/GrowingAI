package com.peter.controller;

import com.github.pagehelper.PageInfo;
import com.peter.bean.File;
import com.peter.bean.FileType;
import com.peter.bean.User;
import com.peter.service.FileService;
import com.peter.service.FileTypeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lcc
 * @date 2020/8/9 11:57
 */
@Controller
@RequestMapping("cpp")
public class CPPController {
    private static final Log LOG = LogFactory.getLog(CPPController.class);
    private static final int TYPE_ID = 2;
    private FileType fileType = null;

    @Autowired
    private FileService fileService;
    @Autowired
    private FileTypeService fileTypeService;

    @GetMapping("list")
    public String list(HttpServletRequest request, Model model) {
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
        model.addAttribute("fileType", fileTypeService.getById(TYPE_ID));
        return "user/fileList";
    }
}
