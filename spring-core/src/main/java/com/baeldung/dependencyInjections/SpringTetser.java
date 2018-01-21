package com.baeldung.dependencyInjections;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTetser {

    public static void main(String[] args) {
        System.out.println("Spring context loading via XMLConfig");
        ApplicationContext xmlContext = new ClassPathXmlApplicationContext("spring_di_config.xml");
        System.out.println("Spring context loading via AnnotationConfig");
        ApplicationContext javaConfigContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    }

}
