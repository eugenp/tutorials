package com.baeldung.springamqpsimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAmqpApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringAmqpApplication.class, args);
    }
}
