package com.baeldung.application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    private ByteArrayOutputStream outContent;

    @Before
    public void setUpPrintStreamInstance() throws Exception {
        this.outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDownByteArrayOutputStream() throws Exception {
        outContent = null;
    }

    @Test
    public void main_NoInputState_TextPrintedToConsole() throws Exception {
        Application.main(new String[]{});
        assertEquals("User found in the collection", outContent.toString());
    }
}