package com.baeldung.spring.patterns.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
 
        Foo foo = context.getBean(Foo.class);
        Bar bar = context.getBean(Bar.class, "Some name");
 
        System.out.println(foo);
        System.out.println("Bar's name: " + bar.getName());
    }
}
