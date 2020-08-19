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
        VirtualMemory virtualMemory = memory.getVirtualMemory();
        memoryInfo.put("total",SizeConvertUtil.Convert(totalByte));
        memoryInfo.put("available",SizeConvertUtil.Convert(availableByte));
        memoryInfo.put("used",SizeConvertUtil.Convert(totalByte-availableByte));

        memoryInfo.put("swapTotal",SizeConvertUtil.Convert(virtualMemory.getSwapTotal()));
        memoryInfo.put("swapAvailable",SizeConvertUtil.Convert(virtualMemory.getSwapUsed()));
        memoryInfo.put("swapUsed",SizeConvertUtil.Convert(virtualMemory.getSwapTotal()-virtualMemory.getSwapUsed()));
        return memoryInfo;
    }
}
