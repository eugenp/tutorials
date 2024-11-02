package com.baeldung.IteratorversusIterable;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IteratorVersusIterableUnitTest {
    private final List<String> employeeDetails = Arrays.asList(
            "Alice Johnson, 30, Manager",
            "Bob Smith, 25, Developer",
            "Charlie Brown, 28, Designer"
    );
    String expectedReport =
            "Employee: Alice Johnson, 30, Manager\n" +
            "Employee: Bob Smith, 25, Developer\n" +
            "Employee: Charlie Brown, 28, Designer\n";


    @Test
    public void givenEmployeeDetails_whenUsingIterator_thenGenerateEmployeeReport() {
        StringBuilder report = new StringBuilder();
        employeeDetails.iterator().forEachRemaining(employee ->
                report.append("Employee: ").append(employee).append("\n")
        );


        assertEquals(expectedReport, report.toString());
    }

    @Test
    public void givenEmployeeDetails_whenUsingForEach_thenGenerateEmployeeReport() {
        StringBuilder report = new StringBuilder();
        employeeDetails.forEach(employee ->
                report.append("Employee: ").append(employee).append("\n")
        );

        assertEquals(expectedReport, report.toString());
    }
}
