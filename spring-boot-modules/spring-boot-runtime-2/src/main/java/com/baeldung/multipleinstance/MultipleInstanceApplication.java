package com.baeldung.multipleinstance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.multipleinstance")
public class MultipleInstanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleInstanceApplication.class, args);
    }

}
