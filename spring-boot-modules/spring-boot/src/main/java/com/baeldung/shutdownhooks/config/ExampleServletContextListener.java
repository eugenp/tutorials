package com.baeldung.shutdownhooks.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ExampleServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Shutdown triggered using ServletContextListener.");
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Triggers when context initializes
    }

}
