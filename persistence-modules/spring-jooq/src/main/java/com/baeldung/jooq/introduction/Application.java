package com.baeldung.jooq.introduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application {
        public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}