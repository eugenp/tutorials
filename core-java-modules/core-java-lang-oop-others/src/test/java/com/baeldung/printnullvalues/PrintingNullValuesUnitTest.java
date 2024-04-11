package com.baeldung.printnullvalues;

import com.baeldung.printnullvalues.Employee;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrintingNullValuesUnitTest {
    Employee employee = new Employee(null, 30, null);
    String expected = "Name: Unknown, Age: 30, Department: Unknown";

    @Test
    public void testToStringNullCheck() {
        assertEquals(expected, employee.toStringUsingNullCheck());
    }

    @Test

    public void testToStringOptional() {
        assertEquals(expected, employee.toStringUsingOptional());
    }

    @Test

    public void testToStringCustomHelper() {
        assertEquals(expected, employee.toStringUsingCustomHelper());

    }

    @Test

    public void testToStringObjects() {
        assertEquals(expected, employee.toStringUsingObjects());
    }
}
