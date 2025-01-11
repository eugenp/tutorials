package com.baeldung.webflux.rsocket.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .main(ClientApplication.class)
                .sources(ClientApplication.class)
                .profiles("client")
                .run(args);
    }
}
