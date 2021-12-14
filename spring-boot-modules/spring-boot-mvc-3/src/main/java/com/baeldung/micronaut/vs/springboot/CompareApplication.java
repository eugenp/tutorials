package com.baeldung.micronaut.vs.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("com.baeldung.micronaut.vs.springboot")
public class CompareApplication {
	public static void main(final String[] args) {
        SpringApplication.run(CompareApplication.class, args);
    }
}
