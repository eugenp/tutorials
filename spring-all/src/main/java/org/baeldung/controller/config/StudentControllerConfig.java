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

        //Manages the lifecycle of the root application context.
        //Conflicts with other root contexts in the application, so we've manually set the parent below.
        //sc.addListener(new ContextLoaderListener(root));

        GenericWebApplicationContext webApplicationContext = new GenericWebApplicationContext();
        webApplicationContext.setParent(root);
        DispatcherServlet dv = new DispatcherServlet(webApplicationContext);

        ServletRegistration.Dynamic appServlet = sc.addServlet("test-mvc", dv);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/test/*");
    }
}
