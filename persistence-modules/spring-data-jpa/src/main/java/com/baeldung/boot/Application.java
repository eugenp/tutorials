package com.baeldung.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.boot.daos.impl.ExtendedRepositoryImpl;
import com.baeldung.multipledb.MultipleDbApplication;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedRepositoryImpl.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
