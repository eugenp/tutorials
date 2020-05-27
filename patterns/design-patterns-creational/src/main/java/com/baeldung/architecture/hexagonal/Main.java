package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.factory.PrinterFactory;
import com.baeldung.architecture.hexagonal.printer.ConsolePrinter;

public class Main {

    public static void main(String[] args) {
        ConsolePrinter printer = PrinterFactory.getConsolePrinter();
        printer.printInterestAmount(2000);
    }
}
