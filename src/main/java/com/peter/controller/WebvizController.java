package com.peter.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lcc
 * @date 2020/7/16 下午9:25
 */
@Controller
@RequestMapping("webviz")
public class WebvizController {
    private Log LOG = LogFactory.getLog(WebvizController.class);

    @Autowired
    private HttpServletRequest request;

    @GetMapping("app")
    public String rosweb(){
        HttpSession session = request.getSession();
        Object user=null;
        user = session.getAttribute("user");
        if (user==null){
            user = session.getAttribute("admin");
        }
        LOG.info("user:"+user+" request webviz app!");
        return "redirect:http://localhost:8080/app";
    }
}
