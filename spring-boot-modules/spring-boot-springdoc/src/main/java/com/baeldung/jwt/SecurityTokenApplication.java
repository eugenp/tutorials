package com.baeldung.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityTokenApplication {

    /**
     * The bootstrap method
     * @param args
     */
    public static void main(String[] args) {
       SpringApplication.run(SecurityTokenApplication.class);
    }
}
