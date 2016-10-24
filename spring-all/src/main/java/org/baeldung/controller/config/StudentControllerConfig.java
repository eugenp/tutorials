package org.baeldung.controller.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class StudentControllerConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(WebConfig.class);

        root.setServletContext(sc);

        // Manages the lifecycle of the root application context
        sc.addListener(new ContextLoaderListener(root));

        DispatcherServlet dv = new DispatcherServlet(new GenericWebApplicationContext());

        ServletRegistration.Dynamic appServlet = sc.addServlet("test-mvc", dv);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/test/*");
    }
}
