package com.peter.controller;

import com.peter.bean.AlgorithmData;
import com.peter.service.VisualService;
import com.peter.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author lcc
 * @date 2020/9/26 20:41
 */
@Controller
@RequestMapping("visual")
@Slf4j
public class VisualController {
    private static final DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private VisualService visualService;

    @GetMapping("param")
    public String param() {

        return "utils/param";
    }
    @GetMapping("data")
    @ResponseBody
    public Map<String,Object> visualData(){
        List<Object> latestAlgorithmDatas = visualService.getLatestAlgorithmDatas();
        long start=Long.MAX_VALUE;
        long end=Long.MIN_VALUE;
        List<String> fieldNames = Arrays.asList("length","points","time");
        Map<String,Object> result=new HashMap<>();
        List<Integer> xLabels=new ArrayList<>();
        int i=0;

        result.put("types",fieldNames);

        for (String fieldName : fieldNames) {
            result.put(fieldName,new ArrayList<Number>());
        }

        for (Object data : latestAlgorithmDatas) {
            AlgorithmData algorithmData= (AlgorithmData) data;
            //时间跨度
            end=Math.max(algorithmData.getTimestamp(),end);
            start=Math.min(algorithmData.getTimestamp(),start);
            //x坐标轴下标
            xLabels.add(++i);

            for (String fieldName : fieldNames) {
                List<Number> list = (List<Number>) result.get(fieldName);
                try {
                    list.add((Number) AlgorithmData.class.getDeclaredField(fieldName).get(algorithmData));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        result.put("xLabels",xLabels);

        String startTime = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(start), ZoneId.systemDefault()));
        String endTime = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(end), ZoneId.systemDefault()));
        result.put("start",startTime);
        result.put("end",endTime);

        return result;
    }
}
