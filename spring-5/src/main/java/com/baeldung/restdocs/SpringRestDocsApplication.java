package com.baeldung.restdocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringRestDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestDocsApplication.class, args);
    }
}
