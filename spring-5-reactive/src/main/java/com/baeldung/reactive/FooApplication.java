package com.baeldung.reactive;

import com.baeldung.reactive.controller.FooReactiveController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FooApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooApplication.class);
    }

}
