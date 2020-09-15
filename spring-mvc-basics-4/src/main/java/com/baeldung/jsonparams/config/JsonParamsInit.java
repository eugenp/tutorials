package com.baeldung.jsonparams.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class JsonParamsInit // implements WebApplicationInitializer 
{

    //uncomment to run the product controller example
    //@Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(JsonParamsConfig.class);
        root.setServletContext(sc);
        sc.addListener(new ContextLoaderListener(root));

        DispatcherServlet dv = new DispatcherServlet(root);

        ServletRegistration.Dynamic appServlet = sc.addServlet("jsonparams-mvc", dv);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }

}
