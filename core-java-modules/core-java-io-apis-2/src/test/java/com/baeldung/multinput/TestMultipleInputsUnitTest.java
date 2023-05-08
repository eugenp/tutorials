package com.baeldung.multinput;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMultipleInputsUnitTest {
    @Test
    public void givenMultipleInputs_whenUsingSpaceDelimiter_thenExpectPrintingOutputs() {
        String input = "10 20\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        mi.UsingSpaceDelimiter();
        // You can add assertions here to verify the behavior of the method
    }

    @Test
    public void givenMultipleInputs_whenUsingREDelimiter_thenExpectPrintingOutputs() {
        String input = "30, 40\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        mi.UsingREDelimiter();
        // You can add assertions here to verify the behavior of the method
    }

    @Test
    public void givenMultipleInputs_whenUsingCustomDelimiter_thenExpectPrintingOutputs() {
        String input = "50; 60\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        mi.UsingCustomDelimiter();
        // You can add assertions here to verify the behavior of the method
    }

    @Test
    public void givenInvalidInput_whenUsingSpaceDelimiter_thenExpectInputMismatchException() {
        String input = "abc\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MultipleInputs mi = new MultipleInputs();
        Assertions.assertThrows(InputMismatchException.class, mi::UsingSpaceDelimiter);
    }
}