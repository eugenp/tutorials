package com.baeldung.boot.count;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
@EnableMongoRepositories(basePackages = { "com.baeldung.boot.count" })
public class SpringBootCountApplication {
    public static void main(String... args) {
        SpringApplication.run(SpringBootCountApplication.class, args);
    }
}
