package com.baeldung.hexagonal.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.baeldung.hexagonal.crud.infrastructure.persistence")
@EnableTransactionManagement
@EntityScan(basePackages="com.baeldung.hexagonal.crud.domain.entity")
public class SpringBootHexagonalCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHexagonalCrudApplication.class, args);
    }

}
