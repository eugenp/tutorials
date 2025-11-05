package com.baeldung.assertj.ignoring;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeUnitTest {

    @Test
    public void givenEmployeesWithDifferentAddresses_whenComparingIgnoringSpecificFields_thenEmployeesAreEqual() {

        Employee employee1 = new Employee();
        employee1.id = 1L;
        employee1.name = "John Doe";
        employee1.department = "Engineering";
        employee1.homeAddress = "123 Home St";
        employee1.workAddress = "456 Work Ave";
        employee1.dateOfBirth = LocalDate.of(1990, 1, 1);
        employee1.grossSalary = 100000.0;
        employee1.netSalary = 75000.0;

        Employee employee2 = new Employee();
        employee2.id = 2L;
        employee2.name = "John Doe";
        employee2.department = "Engineering";
        employee2.homeAddress = "789 Home St";
        employee2.workAddress = "101 Work Ave";
        employee2.dateOfBirth = LocalDate.of(1990, 1, 1);
        employee2.grossSalary = 110000.0;
        employee2.netSalary = 80000.0;

        Assertions.assertThat(employee1)
          .usingRecursiveComparison()
          .ignoringFields("id", "homeAddress", "workAddress", "grossSalary", "netSalary")
          .isEqualTo(employee2);
    }

    @Test
    public void givenEmployeesWithDifferentSalaries_whenComparingIgnoringFieldsMatchingRegex_thenEmployeesAreEqual() {

        Employee employee1 = new Employee();
        employee1.id = 1L;
        employee1.name = "Jane Smith";
        employee1.department = "Marketing";
        employee1.homeAddress = "123 Home St";
        employee1.workAddress = "456 Work Ave";
        employee1.dateOfBirth = LocalDate.of(1990, 1, 1);
        employee1.grossSalary = 95000.0;
        employee1.netSalary = 70000.0;

        Employee employee2 = new Employee();
        employee2.id = 2L;
        employee2.name = "Jane Smith";
        employee2.department = "Marketing";
        employee2.homeAddress = "789 Home St";
        employee2.workAddress = "101 Work Ave";
        employee2.dateOfBirth = LocalDate.of(1990, 1, 1);
        employee2.grossSalary = 98000.0;
        employee2.netSalary = 72000.0;

        Assertions.assertThat(employee1)
          .usingRecursiveComparison()
          .ignoringFields("id")
          .ignoringFieldsMatchingRegexes(".*Address", ".*Salary")
          .isEqualTo(employee2);
    }

    @Test
    public void givenEmployeesWithNullExpectedFields_whenComparingIgnoringExpectedNullFields_thenEmployeesAreEqual() {
        // Given
        Employee expectedEmployee = new Employee();
        expectedEmployee.id = null;
        expectedEmployee.name = "Alice Johnson";
        expectedEmployee.department = null;
        expectedEmployee.homeAddress = null;
        expectedEmployee.workAddress = null;
        expectedEmployee.dateOfBirth = LocalDate.of(1985, 5, 15);
        expectedEmployee.grossSalary = null;
        expectedEmployee.netSalary = null;

        Employee actualEmployee = new Employee();
        actualEmployee.id = 3L;
        actualEmployee.name = "Alice Johnson";
        actualEmployee.department = "HR";
        actualEmployee.homeAddress = "789 Home St";
        actualEmployee.workAddress = "123 Work Ave";
        actualEmployee.dateOfBirth = LocalDate.of(1985, 5, 15);
        actualEmployee.grossSalary = 90000.0;
        actualEmployee.netSalary = 65000.0;

        Assertions.assertThat(actualEmployee)
          .usingRecursiveComparison()
          .ignoringExpectedNullFields()
          .isEqualTo(expectedEmployee);
    }
}
