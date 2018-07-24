package com.baeldung.reactivebackpressuredemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ReactiveBackpressureDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveBackpressureDemoApplication.class, args);
    }
}
