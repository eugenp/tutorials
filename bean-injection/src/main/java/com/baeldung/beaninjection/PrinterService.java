package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PrinterService {

    private IPrinter printer;

    //Constructor based injection
    @Autowired
    public PrinterService(@Qualifier("printer") IPrinter printer) {
        this.printer = printer;
    }

    public void printHellWorld () {
        printer.print("Hello World!");
    }
}
