package org.baeldung.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableAutoConfiguration
@ComponentScan("org.baeldung")
public class MainApplication extends WebMvcConfigurerAdapter {

    public static void main(final String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}