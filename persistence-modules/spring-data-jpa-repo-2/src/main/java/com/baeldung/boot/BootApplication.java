package com.baeldung.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.jpa.JpaApplication;
import com.baeldung.boot.daos.impl.ExtendedRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedRepositoryImpl.class, basePackages = "com.baeldung.boot.daos")
@EntityScan({"com.baeldung.boot.domain"})
@ComponentScan("com.baeldung.boot.daos")
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

}
