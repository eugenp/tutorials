package com.baeldung.dependencyinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.dependencyinjection.model.LaptopContructorInject;
import com.baeldung.dependencyinjection.model.LaptopSetterInject;

public class SpringRunner {

    public static void main(String[] args) {
        LaptopSetterInject laptopSetterInject = getLaptopFromSetterInjection();
        System.out.println(laptopSetterInject.toString());

        LaptopContructorInject laptopContructorInject = getLaptopFromConstructorInjection();
        System.out.println(laptopContructorInject.toString());
    }

    private static LaptopSetterInject getLaptopFromSetterInjection() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SetterInjectConfig.class);

        return context.getBean(LaptopSetterInject.class);

    }

    private static LaptopContructorInject getLaptopFromConstructorInjection() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConstructorInjectConfig.class);

        return context.getBean(LaptopContructorInject.class);

    }

}
