package com.baeldung.web.log.app;

import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.baeldung.web.log.config.CustomeRequestLoggingFilter;

@EnableAutoConfiguration
@ComponentScan("com.baeldung.web.log")
@PropertySource("application-log.properties")
@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}