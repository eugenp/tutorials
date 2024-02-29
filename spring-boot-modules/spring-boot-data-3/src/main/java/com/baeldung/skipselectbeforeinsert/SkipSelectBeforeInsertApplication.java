package com.baeldung.skipselectbeforeinsert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(
    value = "com.baeldung.skipselectbeforeinsert.repository",
    repositoryBaseClass = BaseJpaRepositoryImpl.class
)
public class SkipSelectBeforeInsertApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkipSelectBeforeInsertApplication.class, args);
    }
}
