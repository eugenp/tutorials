package com.baeldung.spring.web.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class MainWebAppInitializer implements WebApplicationInitializer {

    private static final String TMP_FOLDER = "/tmp";
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5 MB

    /**
     * Register and configure all Servlet container components necessary to power the web application.
     */
    @Override
    public void onStartup(final ServletContext sc) throws ServletException {

        // Create the 'root' Spring application context
        final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.scan("com.baeldung.spring.web.config");
        // root.getEnvironment().setDefaultProfiles("embedded");

        sc.addListener(new ContextLoaderListener(root));

        // Handles requests into the application
        final ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setLoadOnStartup(1);

        // final MultipartConfigElement multipartConfigElement = new
        // MultipartConfigElement(TMP_FOLDER, MAX_UPLOAD_SIZE,
        // MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
        //
        // appServlet.setMultipartConfig(multipartConfigElement);

        final Set<String> mappingConflicts = appServlet.addMapping("/");
        if (!mappingConflicts.isEmpty()) {
            throw new IllegalStateException("'appServlet' could not be mapped to '/' due " + "to an existing mapping. This is a known issue under Tomcat versions " + "<= 7.0.14; see https://issues.apache.org/bugzilla/show_bug.cgi?id=51278");
        }
    }

}
