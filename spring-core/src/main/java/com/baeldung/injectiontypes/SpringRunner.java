package com.baeldung.injectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.injectiontypes.domain.Car;

public class SpringRunner {
    public static void main(String[] args) {
        Car toyota = getCarFromJavaConfig();

        System.out.println(toyota);
    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Car.class);
    }

}
