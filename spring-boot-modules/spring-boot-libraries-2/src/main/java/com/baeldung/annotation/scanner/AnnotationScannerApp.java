package com.baeldung.annotation.scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class AnnotationScannerApp {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationScannerApp.class, args);
    }

}
