package com.baeldung.dependency.exception.nonuniquedependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class NonUniqueDependencyRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NonUniqueDependencyRunner.class, args);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
