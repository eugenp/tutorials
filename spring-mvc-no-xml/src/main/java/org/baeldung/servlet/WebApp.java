package org.baeldung.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebApp implements WebApplicationInitializer {

    public WebApp() {
        super();
    }

    // API

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.setServletContext(servletContext);
        root.scan("org.baeldung.spring");
        root.refresh();

        final Dynamic servlet = servletContext.addServlet("mvc", new DispatcherServlet(root));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }

}
