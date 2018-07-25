package com.baeldung.reactive.cors.annotated;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorsOnAnnotatedElementsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CorsOnAnnotatedElementsApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }

}
