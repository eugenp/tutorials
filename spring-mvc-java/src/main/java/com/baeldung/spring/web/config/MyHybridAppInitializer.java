package com.baeldung.spring.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// copied from - spring-mvc-web-vs-initializer 
public class MyHybridAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
    	XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("resources/mvc-configuration.xml");
 
        ServletRegistration.Dynamic dispatcher = container
          .addServlet("dispatcher", new DispatcherServlet(context));
 
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
