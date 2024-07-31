package com.baeldung.spring.security.x509;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class X509AuthenticationServer {

    public static void main(String[] args) {
        SpringApplication.run(X509AuthenticationServer.class, args);
    }

}
