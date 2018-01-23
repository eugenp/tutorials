package com.baeldung.beaninjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryApp {
    public static void main(String args[]) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
        LibrarySetterInjection library = (LibrarySetterInjection) context.getBean("library");
        System.out.println(library.getBook());
    }
}
