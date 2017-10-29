package com.baeldung.constructordi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.constructordi.domain.BioClass;

public class BioClassSpringRunner {
    public static void main(String[] args) {
        BioClass bioClass = getBioClassFromXml();
        
        bioClass.giveBioClassInfo();

        bioClass =  getBioClassJavaConfig();

        bioClass.giveBioClassInfo();
    }

    private static BioClass getBioClassJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BioClassConfig.class);

        return context.getBean(BioClass.class);
    }

    private static BioClass getBioClassFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructordi.xml");

        return context.getBean(BioClass.class);
    }
}
