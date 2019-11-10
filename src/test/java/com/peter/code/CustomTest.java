package com.peter.code;

import com.peter.utils.RegexUtils;

public class CustomTest {
    public static void main(String[] args) {
        String email="123@123.com";
        String number="123456";
        System.out.println(RegexUtils.isEmail(email));
        System.out.println(RegexUtils.isMatch("\\d+",number));
    }
}
