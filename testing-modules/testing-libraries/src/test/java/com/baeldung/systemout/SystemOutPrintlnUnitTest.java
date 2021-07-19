package com.baeldung.systemout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

class SystemOutPrintlnUnitTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void givenSystemOutRedirection_whenInvokePrintln_thenOutputCaptorSuccess() {
        print("Hello Baeldung Readers!!");

        Assert.assertEquals("Hello Baeldung Readers!!", outputStreamCaptor.toString()
            .trim());
    }

    @Test
    void givenTapSystemOut_whenInvokePrintln_thenOutputIsReturnedSuccessfully() throws Exception {

        String text = tapSystemOut(() -> {
            print("Hello Baeldung Readers!!");
        });

        Assert.assertEquals("Hello Baeldung Readers!!", text.trim());
    }

    private void print(String output) {
        System.out.println(output);
    }

}
