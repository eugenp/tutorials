package com.baeldung.spring.kafka.groupId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.spring.kafka.groupId")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
