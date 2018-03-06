/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter2.web.initializer;

import com.sishuok.chapter2.web.servlet.HahaServlet;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-21 下午4:17
 * <p>Version: 1.0
 */
@HandlesTypes(value = HahaServlet.class)
public class HahaServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(final Set<Class<?>> classSet, final ServletContext sc) throws ServletException {
        System.out.println("HahaServletContainerInitializer init");
        System.out.println(classSet);
        for(Class<?> clz : classSet) {
            if(!(Modifier.isInterface(clz.getModifiers()) || Modifier.isAbstract(clz.getModifiers()))) {
                ServletRegistration.Dynamic dynamic = sc.addServlet(clz.getName(), (Class<? extends HahaServlet>)clz);
                String pattern = "/" + clz.getSimpleName().substring(0, 1).toLowerCase() + clz.getSimpleName().substring(1);
                dynamic.addMapping(pattern);
                System.out.println(pattern);
            }

        }



    }
}
