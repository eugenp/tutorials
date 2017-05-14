package com.baeldung.beaninjection.shapeexample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PrinterDriver {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Printer printer = (Printer) context.getBean("printer");
        printer.printShapes();
    }

}
