package com.baeldung.multibeaninstantiation.solution3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.multibeaninstantiation.solution3.Person;

public class SpringApp3 {
    public static void main(String[] args) {
        // Initializing the spring container
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Person person = context.getBean("personOne", Person.class);

        System.out.println(person);

        context.close();
    }
}