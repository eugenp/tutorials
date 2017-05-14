package com.baeldung;

import com.baeldung.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Application presenting various possibilities of bean injections in Spring.
 */
public class App {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Computer obj = (Computer) context.getBean("computer");
        obj.print();
    }
}
