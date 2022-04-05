package com.baeldung.h2db.lazy_load_no_trans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LazyLoadNoTransSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(LazyLoadNoTransSpringBootApplication.class, args);
    }
}
