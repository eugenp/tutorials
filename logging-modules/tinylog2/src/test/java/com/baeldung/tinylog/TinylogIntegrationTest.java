package com.baeldung.tinylog;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tinylog.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class TinylogIntegrationTest {

    private ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();

    @BeforeEach
    public void init() throws UnsupportedEncodingException {
        System.setOut(new PrintStream(consoleOutput, false, "UTF-8"));
    }

    @Test
    public void givenStaticText_shouldLogToConsole() throws UnsupportedEncodingException {
        Logger.info("Hello World!");

        String outputLog = consoleOutput.toString("UTF-8");
        assertThat(outputLog).isEqualToIgnoringNewLines("Hello World!");
    }

    @Test
    public void givenParameterizedText_shouldLogToConsole() throws UnsupportedEncodingException {
        Logger.info("Hello {}!", "Alice");

        String outputLog = consoleOutput.toString("UTF-8");
        assertThat(outputLog).isEqualToIgnoringNewLines("Hello Alice!");
    }

    @Test
    public void givenFormattedNumber_shouldLogToConsole() throws UnsupportedEncodingException {
        Logger.info("π = {0.00}", Math.PI);

        String outputLog = consoleOutput.toString("UTF-8");
        assertThat(outputLog).isEqualToIgnoringNewLines("π = 3.14");
    }

    @Test
    public void givenExceptionWithMessage_shouldLogToConsole() throws UnsupportedEncodingException {
        int a = 42;
        int b = 0;
        try {
            int i = a / b;
        } catch (Exception ex) {
            Logger.error(ex, "Cannot divide {} by {}", a, b);
        }

        String outputLog = consoleOutput.toString("UTF-8");
        assertThat(outputLog).startsWith("Cannot divide 42 by 0: java.lang.ArithmeticException");
    }

    @Test
    public void givenExceptionWithoutMessage_shouldLogToConsole() throws UnsupportedEncodingException {
        try {
            int i = 42 / 0;
        } catch (Exception ex) {
            Logger.error(ex);
        }

        String outputLog = consoleOutput.toString("UTF-8");
        assertThat(outputLog).startsWith("java.lang.ArithmeticException");
    }

}
