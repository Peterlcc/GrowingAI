package com.peter.controller;

import com.peter.utils.LinuxSystemUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author lcc
 * @date 2020/8/19 下午4:11
 */
@Controller
@RequestMapping("/admin/status")
public class SystemStatusController {
    private static final Log LOG= LogFactory.getLog(SystemStatusController.class);
    
    @GetMapping("mem")
    @ResponseBody
    public Map<String,Object> getMemory(){
        Map<String, Object> memoryUsed = LinuxSystemUtil.getMemoryUsed();
        return memoryUsed;
    }
}
