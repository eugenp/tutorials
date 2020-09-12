package com.baeldung.springdispatcherservlet.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// uncomment to run the spring dispatcher example
public class SpringDispatcherServletWebAppInitializer /*implements WebApplicationInitializer*/ {

    //@Override
    public void onStartup(ServletContext container) throws ServletException {
            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
            context.register(DispatcherServletConfig.class);
            context.setServletContext(container);

            ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(context));
            servlet.setLoadOnStartup(1);
            servlet.addMapping("/");
    }
}
