package com.baeldung.springdata.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringDataMongoDBVectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataMongoDBVectorApplication.class, args);
    }
}
