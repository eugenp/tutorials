/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter2.web.listener;

import com.sishuok.chapter2.web.filter.DynamicFilter;
import com.sishuok.chapter2.web.servlet.DynamicServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-21 下午4:02
 * <p>Version: 1.0
 */
@WebListener
public class DynamicInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        sc.addListener("com.sishuok.chapter2.web.listener.DynamicServletContextListener");

        sc.addFilter("dynamicFilter", DynamicFilter.class);

        ServletRegistration.Dynamic dynamic1 = sc.addServlet("dynamicServlet1", DynamicServlet.class);
        dynamic1.setLoadOnStartup(1);
        dynamic1.addMapping("/dynamic1");

        ServletRegistration.Dynamic dynamic2 = sc.addServlet("dynamicServlet2", "com.sishuok.chapter2.web.servlet.DynamicServlet");
        dynamic2.addMapping("/dynamic2");

        ServletRegistration.Dynamic dynamic3 = sc.addServlet("dynamicServlet3", new DynamicServlet());
        dynamic3.addMapping("/dynamic3");

    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
    }
}
