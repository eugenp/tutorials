package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.beaninjection.domain.AutomatedTellerMachine;

public class BeanInjectionSpringRunner {
    public static void main(String[] args) {
        AutomatedTellerMachine atm = getAtmFromJavaConfig();
        System.out.println(atm);
    }

    private static AutomatedTellerMachine getAtmFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanInjectionConfig.class);
        return context.getBean(AutomatedTellerMachine.class);
    }
}
