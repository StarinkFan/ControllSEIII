package com.software2.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if(isAjaxRequest(httpServletRequest)){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());

        }else{
            httpServletResponse.sendRedirect("/login.html");
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request){
        String ajaxFlag=request.getHeader("X-Requested-With");
        return ajaxFlag!=null&&"XMLHttpRequest".equals(ajaxFlag);
    }
}
