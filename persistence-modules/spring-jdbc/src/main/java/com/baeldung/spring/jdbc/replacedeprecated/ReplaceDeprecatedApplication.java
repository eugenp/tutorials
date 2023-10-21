package com.baeldung.spring.jdbc.replacedeprecated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.spring.jdbc.replacedeprecated")
public class ReplaceDeprecatedApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReplaceDeprecatedApplication.class, args);
    }
}
