package com.baeldung.exception.eof;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EOFExceptionDemo3UnitTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void readInput() {
        EOFExceptionDemo3.readInput();
        String expectedOuput = "Input value: 1";
        expectedOuput += "\n" + "Input value: 2";
        expectedOuput += "\n" + "Input value: 3";
        expectedOuput += "\n" + "End of file";
        assertEquals(expectedOuput, outputStreamCaptor.toString()
                .trim());
    }
}