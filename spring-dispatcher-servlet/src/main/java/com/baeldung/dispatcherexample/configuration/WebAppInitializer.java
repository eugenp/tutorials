package com.baeldung.dispatcherexample.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) throws ServletException {
        createProgrammaticXMLDispatcherServlet(container);
        createProgrammaticDispatcherServlet(container);
    }

    private void createProgrammaticXMLDispatcherServlet(ServletContext container) {
        XmlWebApplicationContext xctx = new XmlWebApplicationContext();
        xctx.setConfigLocation("/WEB-INF/context.xml");
        xctx.setServletContext(container);

        ServletRegistration.Dynamic servletTwo = container.addServlet("SpringProgrammaticXMLDispatcherServlet", new DispatcherServlet(xctx));
        servletTwo.setLoadOnStartup(1);
        servletTwo.addMapping("/");
    }

    private void  createProgrammaticDispatcherServlet(ServletContext container) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebMvcConfigure.class);
        ctx.setServletContext(container);

        ServletRegistration.Dynamic servletOne = container.addServlet("SpringProgrammaticDispatcherServlet", new DispatcherServlet(ctx));
        servletOne.setLoadOnStartup(1);
        servletOne.addMapping("/");
    }
}