package com.baeldung.mongodb.dbref;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = { "com.baeldung" })
public class DbRefApplication {

    public static void main(String... args) {
        SpringApplication.run(DbRefApplication.class, args);
    }

}
