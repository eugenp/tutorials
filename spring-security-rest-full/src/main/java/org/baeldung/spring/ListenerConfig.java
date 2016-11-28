package org.baeldung.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;

public class ListenerConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        // Manages the lifecycle of the root application context
        sc.addListener(new RequestContextListener());
    }
}