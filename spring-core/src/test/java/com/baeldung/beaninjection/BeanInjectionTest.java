package com.baeldung.beaninjection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.baeldung.beaninjection.controller.BookController;
import com.baeldung.beaninjection.model.Book;


public class BeanInjectionTest {

    @Test
    public void whenUsingSetter_CheckDependencyValid() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection.setterbasedDI.xml");
        
        BookController bookController = applicationContext.getBean(BookController.class, "bookController");
        
        Book book = new Book("Oliver Twist", "Charles Dickens");
        bookController.addBook("Oliver Twist", "Charles Dickens");
        Book bookFromDI = bookController.getBook("Oliver Twist");
        
        assertEquals(book, bookFromDI);
    }

    @Test
    public void WhenUsingConstructor_CheckDependencyValid() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection.constructorbasedDI.xml");

        BookController bookController = applicationContext.getBean(BookController.class, "bookController");
        
        Book book = new Book("Oliver Twist", "Charles Dickens");
        bookController.addBook("Oliver Twist", "Charles Dickens");
        Book bookFromDI = bookController.getBook("Oliver Twist");

        assertEquals(book, bookFromDI);
    }
}
