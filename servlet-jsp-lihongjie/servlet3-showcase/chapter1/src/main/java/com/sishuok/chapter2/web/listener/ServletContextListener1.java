/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter2.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-21 下午2:47
 * <p>Version: 1.0
 */
@WebListener
public class ServletContextListener1 implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        System.out.println("init servlet context");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        System.out.println("destroy servlet container");
    }
}
