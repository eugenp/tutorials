package com.baeldung.injectiontype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HumanServiceRunnerWithXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("injection-type.xml");
        HumanService humanService = (HumanService) context.getBean(HumanService.class);
        humanService.performAction();
    }
}
