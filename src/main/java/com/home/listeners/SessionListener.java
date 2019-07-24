package com.home.listeners;

import com.home.exception.MyDAOException;
import com.home.dao.IUserImpl;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().setAttribute("name", "Session");
        try {
            IUserImpl userDao = new IUserImpl();
            sessionEvent.getSession().setAttribute("userConnect", userDao);
        } catch (MyDAOException e) {
            e.printStackTrace();
        }
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        try {
            IUserImpl userDao = (IUserImpl) sessionEvent.getSession().getAttribute("userConnect");
            userDao.close();
        } catch (MyDAOException e) {
            e.printStackTrace();
        }
    }
}
