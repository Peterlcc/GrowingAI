package com.peter.config;

import com.peter.utils.MessageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginHandlerInterceptor implements HandlerInterceptor {
    private Log LOG = LogFactory.getLog(UserLoginHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        LOG.info("servletPath: " + servletPath + " sid:" + request.getSession().getId());
        Object admin = request.getSession().getAttribute("admin");
        if (admin !=null)
            return true;
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            LOG.info(servletPath + " is Intercepted");
            LOG.error("没有权限访问，请登录");
            request.setAttribute(MessageUtil.LOGIN_MSG, "没有权限访问，请登录");
            request.getRequestDispatcher( "/login").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
