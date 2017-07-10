package com.baeldung.services;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class TextPrinterTest {

    private ByteArrayOutputStream outContent;
    private TextPrinter textPrinter;

    @Before
    public void setUpPrintStream() {
        outContent =  new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

    }

    @Before
    public void setUpTextPrinterInstance() {
        textPrinter = new TextPrinter(new LowercaseTextService());
    }

    @Test
    public void printText_NoInputState_TextPrintedToConsole() {
        textPrinter.printText("hello from spring");
        assertEquals("hello from spring", outContent.toString());
    }
}