package com.baeldung.di.constructorinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Standalone class to test setter injection.
 *
 */
@Component
public class MainApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student kim = context.getBean(Student.class);
        System.out.println(kim);
    }
}
