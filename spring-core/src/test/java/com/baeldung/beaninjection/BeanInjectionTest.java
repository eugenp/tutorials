package com.baeldung.beaninjection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.baeldung.beaninjection.controller.BookController;
import com.baeldung.beaninjection.model.Book;


public class BeanInjectionTest {

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection.setterbasedDI.xml");
        
        BookController bookController = applicationContext.getBean(BookController.class, "bookController");
        
        Book book = new Book("Oliver Twist", "Charles Dickens");
        bookController.addBook("Oliver Twist", "Charles Dickens");
        Book bookFromDI = bookController.getBook("Oliver Twist");
        
        assertEquals(book, bookFromDI);
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection.constructorbasedDI.xml");

        BookController bookController = applicationContext.getBean(BookController.class, "bookController");
        
        Book book = new Book("Oliver Twist", "Charles Dickens");
        bookController.addBook("Oliver Twist", "Charles Dickens");
        Book bookFromDI = bookController.getBook("Oliver Twist");

        assertEquals(book, bookFromDI);
    }
}
