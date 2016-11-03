package com.baeldung.spring.dispatcher.servlet;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext =
          new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        AnnotationConfigWebApplicationContext webContext =
          new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfiguration.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher",
          dispatcherServlet);
        servlet.addMapping("/*");
        MultipartConfigElement multipartConfigElement =
          new MultipartConfigElement("/tmp");
        servlet.setMultipartConfig(multipartConfigElement);
    }
}
