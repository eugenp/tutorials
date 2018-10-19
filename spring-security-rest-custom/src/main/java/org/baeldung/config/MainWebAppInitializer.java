package org.baeldung.config;

import java.util.Set;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class MainWebAppInitializer implements WebApplicationInitializer {

    public MainWebAppInitializer() {
        super();
    }

    //

    /**
     * Register and configure all Servlet container components necessary to power the web application.
     */
    @Override
    public void onStartup(final ServletContext sc) throws ServletException {
        System.out.println("MyWebAppInitializer.onStartup()");

        // Create the 'root' Spring application context
        final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.scan("org.baeldung.config.parent");
        // root.getEnvironment().setDefaultProfiles("embedded");

        // Manages the lifecycle of the root application context
        sc.addListener(new ContextLoaderListener(root));

        // Handles requests into the application
        final AnnotationConfigWebApplicationContext childWebApplicationContext = new AnnotationConfigWebApplicationContext();
        childWebApplicationContext.scan("org.baeldung.config.child");
        final ServletRegistration.Dynamic appServlet = sc.addServlet("api", new DispatcherServlet(childWebApplicationContext));
        appServlet.setLoadOnStartup(1);
        final Set<String> mappingConflicts = appServlet.addMapping("/");
        if (!mappingConflicts.isEmpty()) {
            throw new IllegalStateException("'appServlet' could not be mapped to '/' due " + "to an existing mapping. This is a known issue under Tomcat versions " + "<= 7.0.14; see https://issues.apache.org/bugzilla/show_bug.cgi?id=51278");
        }

        // spring security filter
        final DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
        final Dynamic addedFilter = sc.addFilter("springSecurityFilterChain", springSecurityFilterChain);
        addedFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
