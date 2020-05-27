package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.factory.PrinterFactory;
import com.baeldung.architecture.hexagonal.printer.Printer;

public class Main {

    public static void main(String[] args) {
        Printer printer = PrinterFactory.getConsolePrinter();
        printer.printInterestAmount(2000);
    }
}
