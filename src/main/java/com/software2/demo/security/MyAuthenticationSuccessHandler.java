package com.software2.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserBLService userBLService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Map<String,Object> map=new HashMap<>();
        String uid= SecurityContextHolder.getContext().getAuthentication().getName();
        if(uid.equals("admin")){
            map.put("result",2);
            map.put("nickname","Admin");
        }
        else {
            map.put("result", 1);
            map.put("nickname", userBLService.getSingle(uid).getName());
        }



        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));

    }
}
