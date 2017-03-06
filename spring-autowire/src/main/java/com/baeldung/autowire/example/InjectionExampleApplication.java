package com.baeldung.autowire.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InjectionExampleApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(InjectionExampleConfiguration.class);
    }
}
