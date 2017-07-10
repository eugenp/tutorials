package com.baeldung.application;

import com.baeldung.services.TextPrinter;
import com.baeldung.services.TextService;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class ApplicationTest {

    private ByteArrayOutputStream outContent;
    private Application application;

    @Before
    public void setUpPrintStream() {
        outContent =  new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

    }

    @Before
    public void setUpApplicationInstance() {
        application = new Application();
    }

    @Test
    public void main_NoInputState_TextPrintedToConsole() {
        Application.main(new String[]{});
        assertEquals("hello from spring", outContent.toString());
    }

    @Test
    public void lowercaseTextService_NoInputState_TextServiceInstance() {
        assertThat(application.lowercaseTextService(), instanceOf(TextService.class));
    }

    @Test
    public void uppercaseTextService_NoInputState_TextServiceInstance() {
        assertThat(application.uppercaseTextService(), instanceOf(TextService.class));
    }

    @Test
    public void textPrinter_NoInputState_TextPrinterInstance() {
        assertThat(application .textPrinter(), instanceOf(TextPrinter.class));
    }
}