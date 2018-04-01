/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-19 上午8:50
 * <p>Version: 1.0
 */
@WebListener
public class MyHttpSessionIdListener implements HttpSessionIdListener {

    @Override
    public void sessionIdChanged(final HttpSessionEvent event, final String oldSessionId) {
        System.out.println("===========session id变更了，老的是：" + oldSessionId + "，新的是：" + event.getSession().getId());
    }
}
