package com.baeldung.persistencecontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.baeldung.persistencecontext")
public class PersistenceContextDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersistenceContextDemoApplication.class, args);
    }
}
