package com.baeldung.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.domain.Config;
import com.baeldung.spring.domain.PC;

public class BootstrapAnnotApp {

    public static void main(String[] args) {
        PC xmlPc = getPCFromXml();

        System.out.println(xmlPc);

        PC jcPC = getPCFromJavaConfig();

        System.out.println(jcPC);

    }

    private static PC getPCFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(PC.class);
    }

    private static PC getPCFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        return context.getBean(PC.class);
    }
}
