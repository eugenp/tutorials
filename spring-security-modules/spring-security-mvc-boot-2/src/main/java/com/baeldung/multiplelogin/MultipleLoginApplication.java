package com.baeldung.multiplelogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-defaults.properties")
public class MultipleLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleLoginApplication.class, args);
    }
}