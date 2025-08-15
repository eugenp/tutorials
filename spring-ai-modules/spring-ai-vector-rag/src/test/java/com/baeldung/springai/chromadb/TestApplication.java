package com.baeldung.springai.chromadb;

import org.springframework.boot.SpringApplication;

public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
            .with(TestcontainersConfiguration.class)
            .run(args);
    }

}