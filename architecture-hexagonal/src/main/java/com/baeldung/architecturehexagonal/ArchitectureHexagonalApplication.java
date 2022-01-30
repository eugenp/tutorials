package com.baeldung.architecturehexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.baeldung.architecturehexagonal.domain.usecases")
public class ArchitectureHexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchitectureHexagonalApplication.class, args);
    }
}
