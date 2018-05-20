package org.baeldung.boot.exitcode.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("DemoExceptionMapping")
@SpringBootApplication
public class ExitCodeExceptionMappingDemo {
    public static void main(String[] args) {
        SpringApplication.run(ExitCodeExceptionMappingDemo.class, args);
    }

    @Bean
    CommandLineRunner createException() {
        return args -> Integer.parseInt("test") ;
    }

    @Bean
    ExitCodeExceptionMapper exitCodeToexceptionMapper() {
        return exception -> {
            //set exit code base on the exception type
            if (exception.getCause() instanceof NumberFormatException) {
                return 80;
            }
            return 1;
        };
    }

}

