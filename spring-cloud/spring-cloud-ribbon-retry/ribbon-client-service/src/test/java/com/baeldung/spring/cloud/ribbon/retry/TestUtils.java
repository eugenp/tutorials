package com.baeldung.spring.cloud.ribbon.retry;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

class TestUtils {

    static int getWebAppPort(ConfigurableApplicationContext ctx) {
        return ((AnnotationConfigServletWebServerApplicationContext) ctx).getWebServer().getPort();
    }

    static void setUpServices(ConfigurableApplicationContext service1, ConfigurableApplicationContext service2) {
        int port1 = getWebAppPort(service1);
        int port2 = getWebAppPort(service2);
        String serversList = String.format("http://localhost:%d, http://localhost:%d", port1, port2);
        System.setProperty("weather-service.ribbon.listOfServers", serversList);
    }
}
