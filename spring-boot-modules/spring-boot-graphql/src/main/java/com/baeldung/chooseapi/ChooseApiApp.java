package com.baeldung.chooseapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChooseApiApp {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "chooseapi");
        SpringApplication.run(ChooseApiApp.class, args);
    }

}
