package com.baeldung.reactive.authresolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class AuthResolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthResolverApplication.class, args);
    }

}
