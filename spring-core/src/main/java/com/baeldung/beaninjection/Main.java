package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        Computer computer = applicationContext.getBean(Computer.class);
        computer.printDetails();
    }
}
