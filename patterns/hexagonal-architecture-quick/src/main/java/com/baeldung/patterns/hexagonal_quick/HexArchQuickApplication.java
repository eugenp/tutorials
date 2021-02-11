package com.baeldung.patterns.hexagonal_quick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.baeldung.patterns.hexagonal_quick")
public class HexArchQuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexArchQuickApplication.class, args);
    }
}