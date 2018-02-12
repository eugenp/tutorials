package com.baeldung.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CheckOutService.class);
        CheckOutService checkOutService = context.getBean(CheckOutService.class);
        checkOutService.checkOut();
    }

}
