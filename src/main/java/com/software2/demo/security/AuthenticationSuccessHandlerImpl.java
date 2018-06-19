package com.software2.demo.security;


import com.alibaba.fastjson.JSON;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Transactional
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    UserBLService uS;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String uid= SecurityContextHolder.getContext().getAuthentication().getName();
        String pw= (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        int i=uS.login(uid,pw);
        System.out.println(i);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("result",i);
        if(i!=0&&i!=2)
            resultMap.put("nickname",uS.getSingle(uid).getName());
        if(i==2)
            resultMap.put("nickname","Admin");
        String json = JSON.toJSONString(resultMap);
        response.getOutputStream().print(json);
    }

}
