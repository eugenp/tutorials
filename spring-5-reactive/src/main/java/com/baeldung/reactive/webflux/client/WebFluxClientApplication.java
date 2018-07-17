package com.baeldung.reactive.webflux.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.baeldung.reactive.webflux.server.WebFluxServerApplication;

@SpringBootApplication
public class WebFluxClientApplication {
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(WebFluxClientApplication.class).properties("server.port=9081").run(args);
    }
}
