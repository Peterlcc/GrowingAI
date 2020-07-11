package com.peter.config;

import com.peter.utils.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lcc
 * @date 2020/7/11 18:55
 */
public class AdminLoginHandlerInterceptor implements HandlerInterceptor {
    private Log LOG = LogFactory.getLog(AdminLoginHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        LOG.info("servletPath: "+servletPath);
        Object admin = request.getSession().getAttribute("admin");
        if (admin == null) {
            LOG.info(servletPath + " is Intercepted");
            LOG.error("没有权限访问，请登录");
            request.setAttribute(MessageUtil.LOGIN_MSG, "没有权限访问，请登录");
            request.getRequestDispatcher("/admin/login").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
