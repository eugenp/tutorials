package com.baeldung.springdata.pgvector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringDataVectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataVectorApplication.class, args);
    }
}
