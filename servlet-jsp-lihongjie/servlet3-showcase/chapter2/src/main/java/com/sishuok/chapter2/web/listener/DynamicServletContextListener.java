/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter2.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-21 下午4:07
 * <p>Version: 1.0
 */
public class DynamicServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        System.out.println("dynamic servlet context init");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        System.out.println("dynamic servlet context destroy");
    }
}
