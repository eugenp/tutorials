package com.baeldung.persistencecontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages="com.baeldung.persistencecontext")
@EnableTransactionManagement
public class PersistenceContextDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersistenceContextDemoApplication.class, args);
    }
}
