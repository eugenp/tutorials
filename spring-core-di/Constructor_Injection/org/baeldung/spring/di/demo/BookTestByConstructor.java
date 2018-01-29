package org.baeldung.spring.di.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BookTestByConstructor {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");
        Book bookObj = (Book) appContext.getBean("book");
        bookObj.bookDetails();
    }

}
