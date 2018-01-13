package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        FieldHelloWorld fieldHelloWorld = context.getBean(FieldHelloWorld.class);
        System.out.println(fieldHelloWorld.getHelloWorldBean());
        ConstructorHelloWorld constructorHelloWorld = context.getBean(ConstructorHelloWorld.class);
        System.out.println(constructorHelloWorld.getHelloWorldBean());
        SetterHelloWorld setterHelloWorld = context.getBean(SetterHelloWorld.class);
        System.out.println(setterHelloWorld.getHelloWorldBean());
    }
}
