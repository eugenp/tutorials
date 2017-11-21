package com.baeldung.springflyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.baeldung.springflyway", "db.migration" })
public class SpringFlywayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFlywayApplication.class, args);
    }
}
