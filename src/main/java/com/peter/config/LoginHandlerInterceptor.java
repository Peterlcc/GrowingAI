package com.peter.config;

import com.peter.controller.PageController;
import com.peter.utils.MessageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    private Log LOG = LogFactory.getLog(LoginHandlerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user==null){
            LOG.error("没有权限访问，请登录");
            request.setAttribute(MessageUtil.LOGIN_MSG,"没有权限访问，请登录");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }else {
            return true;
        }
    }
}
