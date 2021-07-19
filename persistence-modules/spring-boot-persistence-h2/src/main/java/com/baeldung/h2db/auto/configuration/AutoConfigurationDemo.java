package com.baeldung.h2db.auto.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AutoConfigurationDemo {

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationDemo.class, args);
    }

}
