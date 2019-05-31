package com.baeldung.starterparent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarterParentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterParentApplication.class, args);
        System.out.println("Spring boot application running with starter parent");
    }

}
