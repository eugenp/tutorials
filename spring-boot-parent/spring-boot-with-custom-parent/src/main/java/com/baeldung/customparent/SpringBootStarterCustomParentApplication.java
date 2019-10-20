package com.baeldung.customparent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarterCustomParentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterCustomParentApplication.class, args);
        System.out.println("Spring boot application running without starter parent");
    }
}
