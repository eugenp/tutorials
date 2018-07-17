package com.baeldung.reactive.webfluxapplication.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebFluxServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebFluxServerApplication.class).properties("server.port=9080").run(args);
    }
}
