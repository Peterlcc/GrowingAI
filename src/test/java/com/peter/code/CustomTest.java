package com.peter.code;

import com.peter.utils.RegexUtils;
import org.jasypt.util.text.BasicTextEncryptor;

public class CustomTest {
    public static void main(String[] args) {
        String email="123@123.com";
        String number="123456";
        System.out.println(RegexUtils.isEmail(email));
        System.out.println(RegexUtils.isMatch("\\d+",number));
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("growningai");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("973126");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }
}
