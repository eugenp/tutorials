package com.baeldung.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class homeController {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");

        Book book = (Book) context.getBean("book");
        System.out.println(book);
    }

}
