package com.baeldung.gradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootGradle2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGradle2Application.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

}
