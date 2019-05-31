package com.baeldung.springbootstarterwithoutparentexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarterWithoutParentExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterWithoutParentExampleApplication.class, args);
        System.out.println("Spring boot application running without starter parent");
    }
}
