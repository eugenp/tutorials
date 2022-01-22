package com.baeldung.prefix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrefixApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PrefixApplication.class);
        application.setEnvironmentPrefix("prefix");
        application.run(args);
    }
}
