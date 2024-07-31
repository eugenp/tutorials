package com.baeldung.boot.jersey.config;

import com.baeldung.boot.jersey.controllers.HelloController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HelloController.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}