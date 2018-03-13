/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet.chat;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-15 下午10:20
 * <p>Version: 1.0
 */
@WebListener(value = "sessionListener")
public class SessionListener implements HttpSessionListener {

    private MsgPublisher msgPublisher = MsgPublisher.getInstance();

    @Override
    public void sessionCreated(final HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(50); //会话最长50秒
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent se) {
        msgPublisher.logout((String)se.getSession().getAttribute("username"));
    }
}
