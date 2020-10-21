package com.peter.controller;

import com.peter.utils.LinuxSystemUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> results=new HashMap<>();
        List<String> labels=new ArrayList<>();
        List<Map<String,Object>> datas=new ArrayList<>();
        for (String key : memoryUsed.keySet()) {
            labels.add(key);
            Map<String,Object> item=new HashMap<>();
            item.put("name",key);
            item.put("value",memoryUsed.get(key));
            datas.add(item);
        }
        results.put("labels",labels);
        results.put("datas",datas);
        return results;
    }
}
