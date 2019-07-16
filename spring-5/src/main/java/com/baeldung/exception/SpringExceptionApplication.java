package com.baeldung.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = { "com.baeldung.exception" })
public class SpringExceptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringExceptionApplication.class, args);
    }
}