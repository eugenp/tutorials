package com.baeldung.stringwithquotes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrintQuotesAroundAStringUnitTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void replaceOut() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreOut() {
        System.setOut(originalOut);
    }

    @Test
    void whenWrappingAStringWithEscapedQuote_thenGetExpectedResult() {
        String theySay = "All Java programmers are cute!";
        String quoted = "\"" + theySay + "\"";

        System.out.println(quoted);

        //assertion
        String expected = "\"All Java programmers are cute!\"\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void whenCallingReplaceAll_thenGetExpectedResult() {
        String theySay = "Can you write Java code?";
        String quoted = theySay.replaceAll("^|$", "\"");

        System.out.println(quoted);

        //assertion
        String expected = "\"Can you write Java code?\"\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void whenWrappingAStringWithQuoteChar_thenGetExpectedResult() {
        String weSay = "Yes, we can write beautiful Java codes!";
        String quoted = '"' + weSay + '"';
        System.out.println(quoted);

        //assertion
        String expected = "\"Yes, we can write beautiful Java codes!\"\n";
        assertEquals(expected, outContent.toString());
    }
}