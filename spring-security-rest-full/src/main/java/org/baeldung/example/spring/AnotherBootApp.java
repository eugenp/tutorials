package org.baeldung.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("org.baeldung")
public class AnotherBootApp extends WebMvcConfigurerAdapter {

    public static void main(final String[] args) {
        SpringApplication.run(AnotherBootApp.class, args);
    }
}