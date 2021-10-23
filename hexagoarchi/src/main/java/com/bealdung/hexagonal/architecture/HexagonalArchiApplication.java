package com.bealdung.hexagonal.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, CassandraDataAutoConfiguration.class })
public class HexagonalArchiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchiApplication.class, args);
    }

}
