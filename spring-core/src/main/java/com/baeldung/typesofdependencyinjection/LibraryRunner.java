package com.baeldung.typesofdependencyinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryRunner {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("typesofdependencyinjection-context.xml");

        LibraryServiceConstructorInjection constructorInjection
                = (LibraryServiceConstructorInjection) context.getBean("libraryServiceConstructorInjection");
        String bookWithConstructorInjection = constructorInjection.getNewBook("Effective Java");
        System.out.println(bookWithConstructorInjection);

        LibraryServiceSetterInjection setterInjection
                = (LibraryServiceSetterInjection) context.getBean("libraryServiceSetterInjection");
        String bookWithSetterInjection = setterInjection.getNewBook("Spring in Action");
        System.out.println(bookWithSetterInjection);

        LibraryServiceFieldInjection fieldInjection
                = (LibraryServiceFieldInjection) context.getBean("libraryServiceFieldInjection");
        String bookWithFieldInjection = fieldInjection.getNewBook("Spring Microservices in Action");
        System.out.println(bookWithFieldInjection);
    }
}
