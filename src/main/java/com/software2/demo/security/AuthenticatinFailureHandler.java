/*
package com.software2.demo.security;


import com.alibaba.fastjson.JSON;
import com.software2.demo.bean.User;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Transactional
public class AuthenticatinFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    UserBLService userBLService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String uid= SecurityContextHolder.getContext().getAuthentication().getName();
        String pw= (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        int i=userBLService.login(uid,pw);
        System.out.println(i);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("result",i);
        if(i!=0&&i!=2)
            resultMap.put("nickname",userBLService.getSingle(uid).getName());
        if(i==2)
            resultMap.put("nickname","Admin");
        String json = JSON.toJSONString(resultMap);
        response.getOutputStream().print(json);
    }

}
*/
