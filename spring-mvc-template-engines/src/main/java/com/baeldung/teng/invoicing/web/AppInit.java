package com.baeldung.teng.invoicing.web;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

public class AppInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext srvContext) throws ServletException {
        final AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.scan("com.baeldung.teng.invoicing");
        srvContext.addListener(new ContextLoaderListener(appContext));

        final Dynamic dispatcher = srvContext.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
