package com.baeldung.demo;

import java.time.LocalTime;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class demoApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                demoApplication.class,
                args
        );
    }

    @Bean
    ApplicationRunner runner(
            ProductService productService) {

        return args -> {

            System.out.println(
                    LocalTime.now()
                            + " - ApplicationRunner executing."
            );

            productService.printStatus();
        };
    }
}

