package com.baeldung.resttemplate.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RestTemplateWithJsonApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestTemplateWithJsonApplication.class, args);
    }

}
