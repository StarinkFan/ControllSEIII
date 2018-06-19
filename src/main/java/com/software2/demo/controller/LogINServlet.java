package com.software2.demo.controller;

import com.software2.demo.bean.User;
import com.software2.demo.security.MySessionContext;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
public class LogINServlet {
    @Autowired
    UserBLService userBLService;
    private MySessionContext context=MySessionContext.getSessionContext();
    @RequestMapping(value = "/main/login", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request,@RequestBody Map<String, Object> requestMap){
        System.out.println("login");
        String uid=requestMap.get("account").toString();
        String password=requestMap.get("password").toString();
        System.out.println(uid+" "+password);
        int i=userBLService.login(uid,password);
        System.out.println(i);
        HttpSession session=request.getSession();
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("result",i);
        if(i!=0&&i!=2) {
            User u=userBLService.getSingle(uid);
            resultMap.put("nickname", u.getName());
            session.setAttribute("user",u);
            User tempUser=u;
            tempUser.setOnlinestatus(1);
            tempUser.setSessionid(session.getId());
            userBLService.modifyUser(tempUser);

            //删除上一个登录的session
            if(u.getOnlinestatus()==1&&u.getSessionid()!=null){
                HttpSession oldSession=context.getSession(u.getSessionid());
                if(oldSession!=null){
                    oldSession.invalidate();
                }
            }
        }
        if(i==2)
            resultMap.put("nickname","Admin");
        return resultMap;
    }

}
