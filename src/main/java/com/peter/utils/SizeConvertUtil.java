package com.peter.utils;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * @author lcc
 * @date 2020/8/18 下午3:55
 */
public class SizeConvertUtil {
    public static String Convert(long len) {
        String[] units= {"B","KB","MB","GB","TB","PB","EB"};
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (len == 0) {
            return wrongSize;
        }
        double ln=Math.log(len)/Math.log(1024);
        int count=(int)ln;
        if (len < 1) {
            fileSizeString = df.format((double) len);
        } else
            fileSizeString = df.format((double) len / Math.pow(1024, count)) ;
        return fileSizeString+ units[count];
    }
    public static long bytesToGB(long len){
        return len/(long) Math.pow(1024,3);
    }
    public static long bytesToMB(long len){
        return len/(long) Math.pow(1024,2);
    }
    public static long mbToGB(long len){
        return len/1024;
    }
    public static long getBytes(String size){
        String[] units= {"B","KB","MB","GB","TB","PB","EB"};
        String unit = size.substring(size.length() - 2);
        long number= Long.parseLong(size.substring(0,size.length()-2));
        int index= Arrays.binarySearch(units,unit);
        if (index<0||index>=units.length)
        {
            return number;
        }else {
            return number*(long) Math.pow(1024,index);
        }
    }

    public static void main(String[] args) {
        long bytes = getBytes("2GB");
        System.out.println(bytes);
    }
}
