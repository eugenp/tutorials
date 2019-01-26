package com.baeldung.contexts.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootApplicationConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        
        AnnotationConfigWebApplicationContext normalWebAppContext = new AnnotationConfigWebApplicationContext();
        normalWebAppContext.register(NormalWebAppConfig.class);
        ServletRegistration.Dynamic normal = servletContext.addServlet("normal-webapp", new DispatcherServlet(normalWebAppContext));
        normal.setLoadOnStartup(1);
        normal.addMapping("/api/*");
        
        AnnotationConfigWebApplicationContext secureWebAppContext = new AnnotationConfigWebApplicationContext();
        secureWebAppContext.register(SecureWebAppConfig.class);
        ServletRegistration.Dynamic secure = servletContext.addServlet("secure-webapp", new DispatcherServlet(secureWebAppContext));
        secure.setLoadOnStartup(1);
        secure.addMapping("/s/api/*");
    }

}
