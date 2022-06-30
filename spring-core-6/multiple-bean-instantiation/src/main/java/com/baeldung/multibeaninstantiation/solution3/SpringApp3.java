package com.baeldung.multibeaninstantiation.solution3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApp3 {
    public static void main(String[] args) {
        // Initializing the spring container
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersonConfig.class);

        Person person = context.getBean("personOne", Person.class);

        System.out.println(person);

        context.close();
    }
}