package com.baeldung.performancemonitor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PerfomanceApp {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfiguration.class);
        Person person = (Person) context.getBean("person");
        PersonService personService = (PersonService) context.getBean("personService");

        log.info("Name is:" + personService.getFullName(person));
        log.info("Age is:" + personService.getAge(person));

        ((AbstractApplicationContext) context).close();
    }
}
