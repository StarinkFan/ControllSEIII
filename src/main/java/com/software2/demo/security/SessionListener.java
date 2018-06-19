package com.software2.demo.security;

import com.software2.demo.bean.User;
import com.software2.demo.service.UserBLService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class SessionListener implements HttpSessionListener {
    private MySessionContext context = MySessionContext.getSessionContext();

    @Override
    public void sessionCreated(HttpSessionEvent e) {
        context.addSession(e.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        User user = (User)e.getSession().getAttribute("user");

        //获取service
        UserBLService userBLService =(UserBLService) getObjectFromApplication(e.getSession().getServletContext(),"userBLService");

        //获取用户的sessionId和用户在线状态
        User tempUser = userBLService.getSingle(user.getID());

        //如果用户在线且sessionId和e.getSession().getId()相同说明下线，不是更替。
        //则修改用户的在线状态和session设置null。
        if(tempUser.getOnlinestatus() == 1 && e.getSession().getId().equals(tempUser.getSessionid())) {
            tempUser.setOnlinestatus(0);
            tempUser.setSessionid(null);
            userBLService.modifyUser(tempUser);
        }

        context.delSession(e.getSession());
    }

    private Object getObjectFromApplication(ServletContext servletContext, String beanName){
        //通过WebApplicationContextUtils 得到Spring容器的实例。
        ApplicationContext application= WebApplicationContextUtils.getWebApplicationContext(servletContext);
        //返回Bean的实例。
        return application.getBean(beanName);
    }
}
