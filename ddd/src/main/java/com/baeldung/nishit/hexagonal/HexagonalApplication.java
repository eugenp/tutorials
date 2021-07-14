package com.baeldung.nishit.hexagonal;

import com.baeldung.dddhexagonalspring.infrastracture.configuration.CassandraConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.baeldung.nishit.hexagonal.*")
@EnableAutoConfiguration(exclude={CassandraDataAutoConfiguration.class,MongoAutoConfiguration.class})
public class HexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
    }
}
