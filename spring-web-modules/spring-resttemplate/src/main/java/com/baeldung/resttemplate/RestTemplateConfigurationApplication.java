package com.baeldung.resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RestTemplateConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateConfigurationApplication.class, args);
    }
}
