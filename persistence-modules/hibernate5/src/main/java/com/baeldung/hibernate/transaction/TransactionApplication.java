package com.baeldung.hibernate.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung.hibernate.transaction"})
public class TransactionApplication {
    public static void main(String ... args) {
        SpringApplication.run(TransactionApplication.class, args);
    }
}
