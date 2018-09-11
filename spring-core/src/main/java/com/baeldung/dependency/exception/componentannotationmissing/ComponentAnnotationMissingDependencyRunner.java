package com.baeldung.dependency.exception.componentannotationmissing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class ComponentAnnotationMissingDependencyRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ComponentAnnotationMissingDependencyRunner.class, args);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
