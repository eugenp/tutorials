package com.baeldung.exitcode.exceptionexitgen;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExceptionExitCodeGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionExitCodeGeneratorApplication.class, args);
    }

    @Bean
    CommandLineRunner failApplication() {
        return args -> {
            throw new FailedToStartException();
        };
    }
}
