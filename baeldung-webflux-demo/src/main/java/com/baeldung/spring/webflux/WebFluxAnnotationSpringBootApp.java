package com.baeldung.spring.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.spring.webflux")
public class WebFluxAnnotationSpringBootApp {

    public static void main(String[] args) {

        SpringApplication.run(WebFluxAnnotationSpringBootApp.class, args);
    }
}
