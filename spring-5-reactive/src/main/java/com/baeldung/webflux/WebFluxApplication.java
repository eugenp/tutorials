package com.baeldung.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebFluxApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);

        FluxConsumer fluxConsumer = new FluxConsumer();
        fluxConsumer.consumeFlux();
    }
}