package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisableSpringSecurityWithNoDependencies {

    public static void main(String[] args) {
        SpringApplication.run(DisableSpringSecurityWithNoDependencies.class, args);
    }

}
