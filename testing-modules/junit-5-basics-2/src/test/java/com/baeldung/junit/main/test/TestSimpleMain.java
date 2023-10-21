package com.baeldung.junit.main.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSimpleMain {
    private InputStream defaultIn;
    private PrintStream defaultOut;

    @BeforeEach
    public void setUp() {
        defaultIn = System.in;
        defaultOut = System.out;
    }

    @AfterEach
    public void tearDown() {
        System.setIn(defaultIn);
        System.setOut(defaultOut);
    }

    @Test
    public void givenArgumentAsConsoleInput_WhenReadFromSubstitutedByteStream_ThenSuccessfullyCalculate() throws IOException {
        String[] arguments = new String[] { "-i", "CONSOLE" };

        final InputStream fips = new ByteArrayInputStream("1 2 3".getBytes());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setIn(fips);
        System.setOut(out);

        SimpleMain.main(arguments);

        String consoleOutput = byteArrayOutputStream.toString(Charset.defaultCharset());
        assertTrue(consoleOutput.contains("Calculated sum: 6"));

        fips.close();
        out.close();
    }

    @Test
    public void givenArgumentAsConsoleInput_WhenReadFromSubstitutedFileStream_ThenSuccessfullyCalculate() throws IOException {
        String[] arguments = new String[] { "-i", "CONSOLE" };

        final InputStream fips = getClass().getClassLoader()
            .getResourceAsStream("test-input.txt");
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setIn(fips);
        System.setOut(out);

        SimpleMain.main(arguments);

        String consoleOutput = byteArrayOutputStream.toString(Charset.defaultCharset());
        assertTrue(consoleOutput.contains("Calculated sum: 10"));

        fips.close();
        out.close();
    }

    @Test
    public void givenLongArgumentAsConsoleInput_WhenReadFromSubstitutedFileStream_ThenSuccessfullyCalculate() throws IOException {
        String[] arguments = new String[] { "--input", "CONSOLE" };

        final InputStream fips = getClass().getClassLoader()
            .getResourceAsStream("test-input.txt");
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setIn(fips);
        System.setOut(out);

        SimpleMain.main(arguments);

        String consoleOutput = byteArrayOutputStream.toString(Charset.defaultCharset());
        assertTrue(consoleOutput.contains("Calculated sum: 10"));

        fips.close();
        out.close();
    }

}
