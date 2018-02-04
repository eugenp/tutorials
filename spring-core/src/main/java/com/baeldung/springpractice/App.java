package com.baeldung.springpractice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Suyambu
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        ApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
        Processor processor = context.getBean("processor", Processor.class);
        processor.process();

    }
}
