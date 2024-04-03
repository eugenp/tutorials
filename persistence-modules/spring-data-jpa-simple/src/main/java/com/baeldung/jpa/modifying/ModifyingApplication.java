package com.baeldung.jpa.modifying;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.baeldung.jpa.modifying.repository")
@EntityScan("com.baeldung.jpa.modifying.model")
public class ModifyingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModifyingApplication.class, args);
    }

}
