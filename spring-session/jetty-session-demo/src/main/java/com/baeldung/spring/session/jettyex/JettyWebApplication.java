package com.baeldung.spring.session.jettyex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JettyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(JettyWebApplication.class, args);
    }
}
