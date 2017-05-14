package com.baeldung.di.setterinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Standalone class to test setter injection.
 *
 */
public class MainApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student kim = context.getBean(Student.class);
        System.out.println(kim);
    }
}
