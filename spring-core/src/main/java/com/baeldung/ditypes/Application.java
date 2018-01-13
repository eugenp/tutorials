package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        ConstructorHelloWorld constructorHelloWorld = context.getBean(ConstructorHelloWorld.class);
        SetterHelloWorld setterHelloWorld = context.getBean(SetterHelloWorld.class);
        FieldHelloWorld fieldHelloWorld = context.getBean(FieldHelloWorld.class);
        System.out.println(fieldHelloWorld.getHelloWorldBean());
        System.out.println(constructorHelloWorld.getHelloWorldBean());
        System.out.println(setterHelloWorld.getHelloWorldBean());
    }
}
