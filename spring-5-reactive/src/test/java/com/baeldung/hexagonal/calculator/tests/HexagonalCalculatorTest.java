package com.baeldung.hexagonal.calculator.tests;

import learn.to.tdd.hexagonal.calculator.Calculator;
import learn.to.tdd.hexagonal.calculator.ports.Reader;
import learn.to.tdd.hexagonal.calculator.ports.Writer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class HexagonalCalculatorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void staticReaderTest() {
        final Reader reader = () -> Arrays.asList(1, 2);
        final Writer writer = System.out::print;

        final Calculator calculator = new Calculator(reader, writer);
        calculator.add();

        assertEquals("3", outContent.toString());
    }
}
