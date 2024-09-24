package com.baeldung.jpa.subtypes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication 
@EnableJpaRepositories("com.baeldung.jpa.subtypes.repository")
public class SubTypeJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubTypeJpaApplication.class, args);
    }
}
