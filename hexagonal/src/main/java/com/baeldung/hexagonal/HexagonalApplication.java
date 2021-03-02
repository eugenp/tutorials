package com.baeldung.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
    }

}
