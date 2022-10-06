package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.componentscan.springbootapp")
public class SpringBootDiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDiApplication.class, args);
    }
}
