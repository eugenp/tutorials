package org.baeldung.multiplelogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@ComponentScan("org.baeldung.multiplelogin")
public class MultipleLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleLoginApplication.class, args);
    }
}