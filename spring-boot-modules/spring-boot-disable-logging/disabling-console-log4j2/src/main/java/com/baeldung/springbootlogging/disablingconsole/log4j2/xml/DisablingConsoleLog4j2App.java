package com.baeldung.springbootlogging.disablingconsole.log4j2.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisablingConsoleLog4j2App {
    public static void main(String[] args) {
        SpringApplication.run(DisablingConsoleLog4j2App.class, args);
    }
}
