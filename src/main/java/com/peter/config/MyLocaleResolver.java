package com.peter.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Peter on 2019/7/7.
 */
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lan = request.getParameter("lan");
//        Locale locale=Locale.getDefault();
        Locale locale=null;
        if (!StringUtils.isEmpty(lan)){
            String[] split = lan.split("_");
            locale = new Locale(split[0], split[1]);
        }else {
            locale=new Locale("zh","CN");
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
