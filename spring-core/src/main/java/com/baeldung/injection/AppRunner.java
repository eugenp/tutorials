package com.baeldung.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {

    public static void main(String[] args) {
        getCarFromJavaConfig();
        getCarFromXml();
    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        return context.getBean(Car.class);
    }

    private static Car getCarFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("injection-types-context.xml");
        return context.getBean(Car.class);
    }
}
