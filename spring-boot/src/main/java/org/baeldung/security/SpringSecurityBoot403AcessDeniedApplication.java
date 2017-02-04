package org.baeldung.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityBoot403AcessDeniedApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "accessDenied");
       SpringApplication.run(SpringSecurityBoot403AcessDeniedApplication.class, args);
    }
}
