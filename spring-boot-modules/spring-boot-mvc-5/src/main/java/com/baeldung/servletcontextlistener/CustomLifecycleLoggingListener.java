package com.baeldung.servletcontextlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

// Annotate with @WebListener if using @ServletComponentScan in ServletContextListenerApplication
public class CustomLifecycleLoggingListener implements ServletContextListener {

    private final Logger log = LoggerFactory.getLogger(CustomLifecycleLoggingListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info(" Application stopped");
    }
}