package com.baeldung.di.cons;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.cons.domain.Book;

public class Main {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            Book book = context.getBean(Book.class);
            System.out.println(book);
        }
    }

}
