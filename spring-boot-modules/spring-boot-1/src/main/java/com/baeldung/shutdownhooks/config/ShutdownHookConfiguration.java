package com.baeldung.shutdownhooks.config;

import javax.servlet.ServletContextListener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.shutdownhooks.beans.Bean3;

@Configuration
public class ShutdownHookConfiguration {

    @Bean(destroyMethod = "destroy")
    public Bean3 initializeBean3() {
        return new Bean3();
    }

    @Bean
    ServletListenerRegistrationBean<ServletContextListener> servletListener() {
        ServletListenerRegistrationBean<ServletContextListener> srb = new ServletListenerRegistrationBean<>();
        srb.setListener(new ExampleServletContextListener());
        return srb;
    }
}
