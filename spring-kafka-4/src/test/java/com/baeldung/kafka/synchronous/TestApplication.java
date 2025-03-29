package com.baeldung.kafka.synchronous;

import org.springframework.boot.SpringApplication;

class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
            .with(TestcontainersConfiguration.class)
            .run(args);
    }

}