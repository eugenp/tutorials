package com.baeldung.spring.data.jpa.naturalid;

import com.baeldung.spring.data.jpa.naturalid.repository.NaturalIdRepositoryImpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}