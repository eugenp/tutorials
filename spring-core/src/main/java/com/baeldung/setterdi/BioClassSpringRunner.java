package com.baeldung.setterdi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.setterdi.domain.BioClass;

public class BioClassSpringRunner {
    public static void main(String[] args) {
        BioClass bioClass = getBioClassFromXml();

        bioClass.giveBioClassInfo();

        bioClass = getBioFromJavaConfig();

        bioClass.giveBioClassInfo();

    }

    private static BioClass getBioFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BioClassConfig.class);

        return context.getBean(BioClass.class);
    }

    private static BioClass getBioClassFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("setterdi.xml");

        return context.getBean(BioClass.class);
    }
}