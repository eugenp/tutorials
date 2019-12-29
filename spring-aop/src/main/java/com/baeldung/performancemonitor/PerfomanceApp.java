package com.baeldung.performancemonitor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PerfomanceApp {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfiguration.class);
        Person person = (Person) context.getBean("person");
        PersonService personService = (PersonService) context.getBean("personService");
        
        System.out.println("Name is:"+personService.getFullName(person));
        System.out.println("Age is:"+personService.getAge(person));
    }
}
