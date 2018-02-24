package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PrinterApp {

    private PrinterService printerService;

    //Setter based constructor
    @Autowired
    @Qualifier("printerService")
    public void setPrinterService(PrinterService printerService) {
        this.printerService = printerService;
    }

    public void print() {
        printerService.printHellWorld();
    }
}
