package com.dozenx.web.core.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

public class MySessionListener  {
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("addsession");
        MySessionContext.AddSession(httpSessionEvent.getSession());
        }

        public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
            HttpSession session = httpSessionEvent.getSession();
            System.out.println("delsession");
            MySessionContext.DelSession(session);
        }
}
