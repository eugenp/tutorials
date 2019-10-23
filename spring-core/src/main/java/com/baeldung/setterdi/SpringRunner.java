package com.baeldung.setterdi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.setterdi.Config;
import com.baeldung.setterdi.domain.Car;

public class SpringRunner {
    public static void main(String[] args) {
        Car toyota = getCarFromXml();

        System.out.println(toyota);

        toyota = getCarFromJavaConfig();

        System.out.println(toyota);

    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Car.class);
    }

    private static Car getCarFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("setterdi.xml");

        return context.getBean(Car.class);
    }
}