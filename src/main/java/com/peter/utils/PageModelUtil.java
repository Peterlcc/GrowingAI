package com.peter.utils;

import org.springframework.ui.Model;

/**
 * @author lcc
 * @date 2020/9/15 下午5:30
 */
public class PageModelUtil {
    public static void titleAttr(Model model,String title){
        model.addAttribute("title",title);
    }
    public static void loginRegisterAttr(Model model,String title,String type){
        titleAttr(model,title);
        model.addAttribute("type",type);
    }
}
