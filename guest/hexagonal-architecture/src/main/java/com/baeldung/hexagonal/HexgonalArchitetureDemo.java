package com.baeldung.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration

public class HexgonalArchitetureDemo {

    public static void main(String[] args) {
        SpringApplication.run(HexgonalArchitetureDemo.class, args);
    }

}
