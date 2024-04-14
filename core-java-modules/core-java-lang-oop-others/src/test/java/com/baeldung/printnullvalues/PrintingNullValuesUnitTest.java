package com.baeldung.printnullvalues;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrintingNullValuesUnitTest {
    Employee employee = new Employee(null, 30, null);
    String expected = "Name: Unknown, Age: 30, Department: Unknown";

    @Test
    public void givenNullValues_whenToStringUsingNullCheck_thenCorrectStringReturned() {
        assertEquals(expected, employee.toStringUsingNullCheck());
    }

    @Test
    public void givenNullValues_whenToStringUsingOptional_thenCorrectStringReturned() {
        assertEquals(expected, employee.toStringUsingOptional());
    }

    @Test
    public void givenNullValues_whenToStringUsingCustomHelper_thenCorrectStringReturned() {
        assertEquals(expected, employee.toStringUsingCustomHelper());
    }

    @Test
    public void givenNullValues_whenToStringUsingObjects_thenCorrectStringReturned() {
        assertEquals(expected, employee.toStringUsingObjects());
    }
}
