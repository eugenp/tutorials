package com.baeldung.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.baeldung.application"})
@EntityScan("com.baeldung.application.entities")
@EnableJpaRepositories("com.baeldung.application.repositories")public class SpringBootConsoleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class);
    }
}
