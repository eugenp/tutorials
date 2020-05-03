package com.baeldung.web.log.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.WebApplicationInitializer;

public class CustomWebAppInitializer implements WebApplicationInitializer {
    
    @Override
    public void onStartup(ServletContext container) throws ServletException {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.baeldung.web.log");
        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/"); 

        container.addFilter("customRequestLoggingFilter", CustomeRequestLoggingFilter.class).addMappingForServletNames(null, false, "dispatcher");
    }
}