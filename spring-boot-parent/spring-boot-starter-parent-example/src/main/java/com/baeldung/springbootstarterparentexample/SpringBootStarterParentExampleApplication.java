package com.baeldung.springbootstarterparentexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarterParentExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterParentExampleApplication.class, args);
        System.out.println("Spring boot application running with starter parent");
    }

}
