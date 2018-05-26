package com.baeldung.springwebflux.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = { "com.baeldung" })
@EnableWebFlux
public class SpringWebFluxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebFluxDemoApplication.class, args);
    }
}
