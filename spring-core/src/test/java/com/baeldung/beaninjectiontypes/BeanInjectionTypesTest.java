package com.baeldung.beaninjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeanInjectionTypesTest {
    @SuppressWarnings("resource")
    @Test
    public void LibrarySetterInjectionTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("setterinjection.xml");
        LibrarySetterInjection library = (LibrarySetterInjection) context.getBean("library");
        String name = "Data Structures and Algorithms";
        assertNotNull(library);
        assertTrue(name.equals(library.getBook().getName()));
    }

    @SuppressWarnings("resource")
    @Test
    public void LibraryConstructorInjectionTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructorinjection.xml");
        LibraryConstructorInjection library = (LibraryConstructorInjection) context.getBean("library");
        String name = "Data Structures and Algorithms";
        assertNotNull(library);
        assertTrue(name.equals(library.getBook().getName()));
    }
}
