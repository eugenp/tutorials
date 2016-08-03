package com.baeldung.spring.security.x509;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class X509AuthenticationServer {

    public static void main(String[] args) {
        SpringApplication.run(X509AuthenticationServer.class, args);
    }
}
