package com.baeldung.auth.server.multitenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultitenantAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultitenantAuthServerApplication.class, args);
    }
}
