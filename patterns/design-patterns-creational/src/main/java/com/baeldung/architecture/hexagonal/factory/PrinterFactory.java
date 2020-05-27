package com.baeldung.architecture.hexagonal.factory;

import com.baeldung.architecture.hexagonal.printer.Printer;
import com.baeldung.architecture.hexagonal.printer.impl.ConsolePrinterImpl;

public class PrinterFactory {
    public PrinterFactory() {
        super();
    }

    public static Printer getConsolePrinter() {
        return new ConsolePrinterImpl();
    }
}
