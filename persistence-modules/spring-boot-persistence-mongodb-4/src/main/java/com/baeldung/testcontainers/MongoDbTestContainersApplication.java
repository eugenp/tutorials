package com.baeldung.testcontainers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = { "com.baeldung" })
public class MongoDbTestContainersApplication {

    public static void main(String... args) {
        SpringApplication.run(MongoDbTestContainersApplication.class, args);
    }

}
