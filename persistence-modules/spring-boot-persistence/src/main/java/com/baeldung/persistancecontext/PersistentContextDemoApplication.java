package com.baeldung.persistancecontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.baeldung.persistancecontext")
public class PersistentContextDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersistentContextDemoApplication.class, args);
    }
}
