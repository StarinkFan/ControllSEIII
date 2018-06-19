package com.software2.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("在 登录拦截器。 ");
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null) {
            System.out.println("Already Log In");
            return true;
        }
        System.out.println("Not Log In");
        request.getRequestDispatcher("/login.html").forward(request, response);
        return false;
    }
}
