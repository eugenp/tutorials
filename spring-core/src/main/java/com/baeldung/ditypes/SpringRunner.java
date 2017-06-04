package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.ditypes.domain.Book;

public class SpringRunner {

    public static void main(String[] args) {
        Book classicBook = getBookFromXml();
        System.out.println(classicBook);
    }

    private static Book getBookFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ditypes.xml");
        Book book = context.getBean(Book.class);
        return context.getBean(Book.class);
    }
}
