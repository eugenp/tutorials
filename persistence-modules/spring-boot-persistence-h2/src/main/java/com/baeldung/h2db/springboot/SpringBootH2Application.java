package com.baeldung.h2db.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-h2.properties")
public class SpringBootH2Application {

    public static void main(String... args) {
        SpringApplication.run(SpringBootH2Application.class, args);
    }
}
