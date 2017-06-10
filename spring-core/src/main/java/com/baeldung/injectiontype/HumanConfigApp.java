package com.baeldung.injectiontype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.setterdi.Config;
import com.baeldung.setterdi.domain.Car;

public class HumanConfigApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("injection-type.xml");
        HumanService humanService = (HumanService) context.getBean("humanService");
        humanService.performAction();
    }

   
}