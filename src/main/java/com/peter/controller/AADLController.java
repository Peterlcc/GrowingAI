package com.peter.controller;

import com.github.pagehelper.PageInfo;
import com.peter.bean.File;
import com.peter.bean.FileType;
import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.service.FileService;
import com.peter.service.FileTypeService;
import com.peter.service.GeneratorService;
import com.peter.utils.FileUtil;
import com.peter.utils.MessageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/8/9 11:56
 */
@Controller
@RequestMapping("aadl")
public class AADLController {
    private static final Log LOG = LogFactory.getLog(AADLController.class);
    private static final int TYPE_ID=1;
    private FileType fileType=null;

    @Autowired
    private FileService fileService;
    @Autowired
    private FileTypeService fileTypeService;
    @Autowired
    private GrowningAiConfig growningAiConfig;
    @Autowired
    private GeneratorService generatorService;

    @PostMapping("aadl")
    public String upload(@RequestParam("aadl") MultipartFile aadl,File file, HttpServletRequest request,
                         RedirectAttributes model){
        if (aadl == null) {
            LOG.info("aadl to upload is null,upload failed!");
            model.addFlashAttribute(MessageUtil.UPLOAD_MSG,"上传项目的文件为空，请确认上传的项目！");
            return "redirect:/aadl/add";
        }
        User user = (User) request.getSession().getAttribute("user");
        file.setUserId(user.getId());
        file.setCreatetime(new Date());
        if (fileType==null){
            fileType=fileTypeService.getById(TYPE_ID);
        }
        String path=growningAiConfig.getFilesDir() + java.io.File.separator+user.getNumber()+java.io.File.separator+fileType.getName()+ java.io.File.separator;
        String[] pathNames = aadl.getOriginalFilename().split("/");
        file.setPath(path+ pathNames[0]);
        java.io.File addlDir = new java.io.File(file.getPath());
        if (addlDir.exists()){
            LOG.info("addlDir is existed,upload failed!");
            model.addFlashAttribute(MessageUtil.UPLOAD_MSG,"上传文件的已存在，请确认上传的文件！");
            return "redirect:/aadl/add";
        }
        LOG.info("upload files:"+aadl.getOriginalFilename());
        String msg = FileUtil.save(path, aadl);
        if (!msg.equals("上传成功")) {
            LOG.info(msg);
            model.addFlashAttribute(MessageUtil.UPLOAD_MSG,msg);
            return "redirect:/aadl/add";
        }
        fileService.save(file);
        LOG.info("save file:" + file);
        return "redirect:/aadl/list";
    }

    @GetMapping("list")
    public String listAADL(HttpServletRequest request, Model model){
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
        LOG.info("list:"+pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        if (fileType==null){
            fileType=fileTypeService.getById(TYPE_ID);
        }
        model.addAttribute("fileType",fileType);
        model.addAttribute("fileType",fileTypeService.getById(TYPE_ID));
        return "user/fileList";
    }

//    @DeleteMapping("file")
    @PostMapping("file")
    @ResponseBody
    public String deleteFile(HttpServletRequest request,@RequestParam("id")Integer id){
        LOG.info("deleteFile , id is:"+id);
        File file = fileService.getById(id);
        try {
            if (file.getTypeId()==3)
                FileUtils.deleteDirectory(new java.io.File(file.getPath()));
            else
                FileUtils.forceDelete(new java.io.File(file.getPath()));
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        boolean delete = fileService.delete(id);
        LOG.info("file " + file.getName() + " is deleted");
        return "删除成功";
    }
    @PostMapping("code")
    @ResponseBody
    public String codeGenerate(Integer id,String task,String require,String env,HttpServletRequest request){
        LOG.info("get params:["+String.join(",",task,require,env)+"]");
        User user = (User) request.getSession().getAttribute("user");
        File file = fileService.getById(id);

        Map<String,String> params=new HashMap<>();
        params.put("env",env);
        params.put("require",require);
        params.put("task",task);

        File generated = generatorService.generate(file,user,params);
        LOG.info("generated:"+generated);
        fileService.save(generated);
        return "自动生成代码已提交,请稍后到生成项目列表查看";
    }
}
