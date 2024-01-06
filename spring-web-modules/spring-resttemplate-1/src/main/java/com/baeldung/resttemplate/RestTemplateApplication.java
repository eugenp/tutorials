package com.baeldung.resttemplate;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestTemplateApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestTemplateApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.servlet.encoding.charset", "ISO-8859-1"));
        app.run(args);
    }
}
