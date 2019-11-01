package com.baeldung.springbootlogging.disablingconsole.logback.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisablingConsoleLogbackApp {
    public static void main(String[] args) {
        SpringApplication.run(DisablingConsoleLogbackApp.class, args);
    }
}
