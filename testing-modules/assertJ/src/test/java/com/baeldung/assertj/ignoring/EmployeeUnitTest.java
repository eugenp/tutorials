package com.baeldung.assertj.ignoring;

import java.time.LocalDate;
import java.util.List;

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

    @Test
    public void givenEmployees_whenComparingIgnoringFields_thenContainsEqualElements() {
        Employee actual1 = new Employee();
        actual1.id = 1L;
        actual1.name = "John Doe";
        actual1.department = "Engineering";
        actual1.homeAddress = "123 Home St";
        actual1.workAddress = "456 Work Ave";
        actual1.dateOfBirth = LocalDate.of(1990, 1, 1);
        actual1.grossSalary = 100000.0;
        actual1.netSalary = 75000.0;

        Employee actual2 = new Employee();
        actual2.id = 2L;
        actual2.name = "John Doe";
        actual2.department = "Engineering";
        actual2.homeAddress = "789 Home St";
        actual2.workAddress = "101 Work Ave";
        actual2.dateOfBirth = LocalDate.of(1990, 1, 1);
        actual2.grossSalary = 110000.0;
        actual2.netSalary = 80000.0;

        Employee expected1 = new Employee();
        expected1.id = 999L;
        expected1.name = "John Doe";
        expected1.department = "Engineering";
        expected1.homeAddress = "Nil";
        expected1.workAddress = "Nil";
        expected1.dateOfBirth = LocalDate.of(1990, 1, 1);
        expected1.grossSalary = 0.0;
        expected1.netSalary = 0.0;

        Employee expected2 = new Employee();
        expected2.id = 1000L;
        expected2.name = "John Doe";
        expected2.department = "Engineering";
        expected2.homeAddress = "Nil";
        expected2.workAddress = "Nil";
        expected2.dateOfBirth = LocalDate.of(1990, 1, 1);
        expected2.grossSalary = 0.0;
        expected2.netSalary = 0.0;

        List<Employee> actual = List.of(actual1, actual2);
        List<Employee> expected = List.of(expected1, expected2);

        Assertions.assertThat(actual)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "homeAddress", "workAddress", "grossSalary", "netSalary")
            .containsExactlyInAnyOrderElementsOf(expected);
    }
}
