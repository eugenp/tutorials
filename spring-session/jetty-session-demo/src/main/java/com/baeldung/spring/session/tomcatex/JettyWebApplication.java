package com.baeldung.spring.session.tomcatex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JettyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(JettyWebApplication.class, args);
    }
}
