package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ditypes.domain.Book;

public class SpringRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

        Book bookConstructorInjection = (Book) context.getBean("bookJavaConstructor");
        System.out.println(bookConstructorInjection);

        Book bookSetterInjection = (Book) context.getBean("bookJavaSetter");
        System.out.println(bookSetterInjection);
    }
}
