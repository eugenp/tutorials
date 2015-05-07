package org.baeldung.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Main Application Class - uses Spring Boot. Just run this as a normal Java
 * class to run up a Jetty Server (on http://localhost:8080)
 *
 */
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("org.baeldung")
public class Application extends WebMvcConfigurerAdapter {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}