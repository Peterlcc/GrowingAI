package com.peter.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lcc
 * @date 2020/9/26 20:41
 */
@Controller
@RequestMapping("visual")
public class VisualController {
    private static final Log LOG= LogFactory.getLog(VisualController.class);

    @GetMapping("param")
    public String param(){

        return "utils/param";
    }
}
