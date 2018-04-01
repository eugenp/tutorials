/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter2.web.initializer;

import com.sishuok.chapter2.web.servlet.DynamicServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-21 下午4:17
 * <p>Version: 1.0
 */
public class MyServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(final Set<Class<?>> c, final ServletContext sc) throws ServletException {
        System.out.println("MyServletContainerInitializer init");
        ServletRegistration.Dynamic dynamic = sc.addServlet("dynamicServlet4", DynamicServlet.class);
        dynamic.addMapping("/dynamic4");

        sc.getServletRegistrations().get("dynamicServlet4").addMapping("/dynamic41");

    }
}
