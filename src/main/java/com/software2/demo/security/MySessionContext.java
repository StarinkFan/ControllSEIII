package com.software2.demo.security;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class MySessionContext {
    private static MySessionContext context;

    private Map<String, HttpSession> map;

    private MySessionContext() {
        map = new HashMap<>();
    }

    public static MySessionContext getSessionContext() {
        if(context == null) {
            context = new MySessionContext();
        }
        return context;
    }

    //添加
    public synchronized void addSession(HttpSession session) {
        if(session!= null)
            map.put(session.getId(), session);
    }

    //获取
    public synchronized HttpSession getSession(String sessionId) {
        if(sessionId == null)
            return null;
        return map.get(sessionId);
    }

    //删除
    public synchronized void delSession(HttpSession session) {
        if(session!= null)
            map.remove(session.getId());
    }
}
