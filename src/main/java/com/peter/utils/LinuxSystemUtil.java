package com.peter.utils;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/8/18 下午3:44
 */
public class LinuxSystemUtil {
    private static final SystemInfo systemInfo=new SystemInfo();
    private static final Map<String,Object> memoryInfo=new HashMap<>();
    public static Map<String,Object> getMemoryUsed(){
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long availableByte = memory.getAvailable();
//        VirtualMemory virtualMemory = memory.getVirtualMemory();
//        memoryInfo.put("total",SizeConvertUtil.bytesToMB(totalByte));
        memoryInfo.put("未使用",SizeConvertUtil.bytesToMB(availableByte));
        memoryInfo.put("已使用",SizeConvertUtil.bytesToMB(totalByte-availableByte));

//        memoryInfo.put("swapTotal",SizeConvertUtil.bytesToMB(virtualMemory.getSwapTotal()));
//        memoryInfo.put("swapAvailable",SizeConvertUtil.bytesToMB(virtualMemory.getSwapUsed()));
//        memoryInfo.put("swapUsed",SizeConvertUtil.bytesToMB(virtualMemory.getSwapTotal()-virtualMemory.getSwapUsed()));
        return memoryInfo;
    }

    public static void main(String[] args) {
        Map<String, Object> memoryUsed = getMemoryUsed();
        memoryUsed.forEach((key,value)->{
            System.out.println(key+"---"+value);
        });
    }
}
