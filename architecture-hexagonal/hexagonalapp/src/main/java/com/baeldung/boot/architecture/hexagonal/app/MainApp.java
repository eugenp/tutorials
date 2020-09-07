package com.baeldung.boot.architecture.hexagonal.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp {


    public static void main(String[] args) {
        init();
        SpringApplication.run(MainApp.class, args);
    }

    // Here we can initialise some data if needed
    private static void init() {

        System.out.println("Hexagonal architecture application is starting up!");
    }

}