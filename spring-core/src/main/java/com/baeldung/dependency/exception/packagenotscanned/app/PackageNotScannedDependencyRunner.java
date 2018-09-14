package com.baeldung.dependency.exception.packagenotscanned.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
public class PackageNotScannedDependencyRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PackageNotScannedDependencyRunner.class, args);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
