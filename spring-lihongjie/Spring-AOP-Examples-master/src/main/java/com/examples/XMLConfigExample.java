package com.examples;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XMLConfigExample {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-aop-beans.xml");
        Helloworld helloworld = context.getBean(Helloworld.class);
        helloworld.printHelloWorld();
        context.close();
    }
}
