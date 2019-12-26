package com.baeldung.couchbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ReactiveCouchbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveCouchbaseApplication.class, args);
    }
}
