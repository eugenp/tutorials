package com.baeldung.architecture.hexagonal.factory;

import com.baeldung.architecture.hexagonal.printer.ConsolePrinter;
import com.baeldung.architecture.hexagonal.printer.impl.ConsolePrinterImpl;

public class PrinterFactory {
    public PrinterFactory() {
        super();
    }

    public static ConsolePrinter getConsolePrinter() {
        return new ConsolePrinterImpl();
    }
}
