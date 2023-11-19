package com.baeldung.rootmapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class RootMappingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RootMappingApplication.class, args);
    }
}