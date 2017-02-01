package com.baeldung.fieldinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.constructordi.domain.Car;

public class SpringRunner {
    public static void main(String[] args) {
        Bike bmw = getBikeFromJavaConfig();

        System.out.println(bmw);
    }

    private static Bike getBikeFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Bike.class);
    }
}
