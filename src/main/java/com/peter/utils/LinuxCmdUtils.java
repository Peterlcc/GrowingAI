package com.peter.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

public class LinuxCmdUtils {
    private static Log logger = LogFactory.getLog(LinuxCmdUtils.class);

    public  static boolean executeLinuxCmd(String cmd) {

        boolean result=false;

        logger.info("got cmd : " + cmd);
        Runtime run = Runtime.getRuntime();
        //InputStream in=null;
        try {
            Process process = run.exec(cmd);
            //执行结果 0 表示正常退出
            int exeResult=process.waitFor();
            if(exeResult==0){
                logger.info("命令执行成功:"+cmd);
                result=true;
            }

        }
        catch (Exception e) {
            logger.error("LinuxCmdUtils.executeLinuxCmd error",e);
        }
        return result;
    }
    public  static boolean executeLinuxCmdWithPath(String cmd,String basePath) {

        boolean result=false;

        logger.info("got cmd : " + cmd);
        Runtime run = Runtime.getRuntime();
        //InputStream in=null;
        try {
            Process process = run.exec(new String[] {"/bin/bash","-c",cmd},null,new File(basePath));
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            InputStreamReader ie = new InputStreamReader(process.getErrorStream());
            LineNumberReader input = new LineNumberReader(ir);
            LineNumberReader error = new LineNumberReader(ie);
            //执行结果 0 表示正常退出
            int exeResult=process.waitFor();
            if(exeResult==0){
                logger.info("命令执行成功:"+cmd);
                result=true;
            }
            String line;
            while ((line = input.readLine()) != null){
                logger.info("get result:"+line);
            }
            while ((line = error.readLine()) != null){
                logger.error("get result:"+line);
            }

        }
        catch (Exception e) {
            logger.error("LinuxCmdUtils.executeLinuxCmd error",e);
        }
        return result;
    }
    /**
     * 获取linux命令执行的结果,cat 之类
     * @param cmd
     * @return
     */
    public static String getCmdResult(String cmd) {

        String result = "";
        try {

            Process process = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null){
                result=line;
            }

        } catch (java.io.IOException e) {

            System.err.println("IOException " + e.getMessage());

        }
        return result;
    }

    /**
     * grep 类的shell命令
     * @param cmdStr
     * @return
     */
    public static String getGrepCmdReturn(String cmdStr){

        String[] cmd = new String[3];

        cmd[0]="/bin/sh";
        cmd[1]="-c";
        cmd[2]=cmdStr;

        //得到Java进程的相关Runtime运行对象
        Runtime runtime = Runtime.getRuntime();
        StringBuffer stringBuffer=null;
        try
        {

            Process process = runtime.exec(cmd);

            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            stringBuffer= new StringBuffer();

            String temp = null;

            while ((temp = bufferReader.readLine()) != null)
            {
                stringBuffer.append(temp);
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return  stringBuffer.toString();
    }
    public  static boolean executeDirCmd(String cmd,String base) {

        boolean result=false;

        logger.info("got cmd : " + cmd);
        Runtime run = Runtime.getRuntime();
        //InputStream in=null;
        try {
            Process process = run.exec(cmd);
            //执行结果 0 表示正常退出
            int exeResult=process.waitFor();
            if(exeResult==0){
                logger.info("执行成功");
                result=true;
            }

        }
        catch (Exception e) {
            logger.error("LinuxCmdUtils.executeLinuxCmd error",e);
        }
        return result;
    }

    public static void main(String[] args) {

        System.out.println(LinuxCmdUtils.getGrepCmdReturn("dmidecode -s system-serial-number|grep -v \"#\""));
    }
}
