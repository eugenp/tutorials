package com.baeldung.scala

import org.junit.Test
import org.junit.Assert._

class EmployeeUnitTest {

    @Test
    def whenEmployeeSalaryIncremented_thenCorrectSalary() = {
        val employee = new Employee("John Doe", 1000);
        employee.incrementSalary();
        assertEquals(1020, employee.salary);
    }

    @Test
    def givenEmployee_whenToStringCalled_thenCorrectStringReturned() = {
        val employee = new Employee("John Doe", 1000);
        assertEquals("Employee(name=John Doe, salary=1000)", employee.toString);
    }
}


