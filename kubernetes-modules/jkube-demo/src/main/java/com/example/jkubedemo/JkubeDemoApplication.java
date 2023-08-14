package com.example.jkubedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JkubeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JkubeDemoApplication.class, args);
        System.out.println("Hello from JKube Demo");
    }

}
