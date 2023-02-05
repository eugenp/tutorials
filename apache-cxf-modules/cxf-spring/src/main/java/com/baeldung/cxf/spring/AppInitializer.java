package com.baeldung.cxf.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


import jakarta.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ServiceConfiguration.class);
        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = (ServletRegistration.Dynamic) container.addServlet("dispatcher", new CXFServlet());
        dispatcher.addMapping("/services/*");
    }
}