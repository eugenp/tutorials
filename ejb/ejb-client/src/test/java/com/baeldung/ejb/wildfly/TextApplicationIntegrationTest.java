package com.baeldung.ejb.wildfly;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.naming.NamingException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class TextApplicationIntegrationTest {

    private static ByteArrayOutputStream outContent;

    @BeforeClass
    public static void setUpPrintStreamInstance() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterClass
    public static void tearDownByteArrayOutputStream() {
        outContent = null;
    }

    @Test
    public void givenInputString_whenCompareTtoStringPrintedToConsole_thenSuccessful() throws NamingException {
        TextApplication.main(new String[]{});
        assertEquals("SAMPLE TEXT", outContent.toString());
    }
}