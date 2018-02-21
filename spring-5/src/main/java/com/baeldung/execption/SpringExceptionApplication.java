package com.baeldung.execption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = { "com.baeldung.execption" })
public class SpringExceptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringExceptionApplication.class, args);
    }
}