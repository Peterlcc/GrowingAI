package com.peter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.peter.bean.SysModel;
import com.peter.bean.User;
import com.peter.bean.UserModel;
import com.peter.service.SysModelService;
import com.peter.service.UserModelService;
import com.peter.utils.MessageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lcc
 * @date 2020/5/17 13:26
 */
@Controller
@RequestMapping("model")
public class ModelController {
    private Log LOG = LogFactory.getLog(ModelController.class);

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserModelService userModelService;
    @Autowired
    private SysModelService sysModelService;

    @GetMapping("osate")
    public String osate(Model model){
        LOG.info("design/osate.html requested!");
        List<UserModel> models = userModelService.getAll();
        models.forEach(m->{
            m.setJson(null);
        });
        model.addAttribute("models",models);
        return "design/osate";
    }
    @PostMapping("save")
    @ResponseBody
    public String saveModel(UserModel userModel){
        userModelService.save(userModel);
        User user = (User) request.getSession().getAttribute("user");
        LOG.info("model:"+userModel.getName()+"of "+user.getName()+" is saved!");
        return "SUCCEED";
    }

    @GetMapping("load")
    @ResponseBody
    public String load(@RequestParam("id") Integer id){
        UserModel userModel = userModelService.getModelById(id);
        return userModel.getJson();
    }
    @GetMapping("init")
    @ResponseBody
    public String init(){
        List<SysModel> sysModels = sysModelService.getAll();
        JSONArray jsonArray = new JSONArray();
        sysModels.forEach(sysModel -> {
            JSONObject jsonObject = JSON.parseObject(sysModel.getJson());
            jsonArray.add(jsonObject);
        });
        return jsonArray.toString();
    }
}
