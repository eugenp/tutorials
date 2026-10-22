package com.baeldung.assertj.ignoring;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeUnitTest {

    @Test
    public void givenEmployeesWithDifferentFields_whenComparingIgnoringSpecificFields_thenEmployeesAreEqual() {

        Employee employee1 = new Employee(1L, "John Doe", "Engineering", "456 Work Ave", 7500.0, 10000.0);
        Employee employee2 = new Employee(2L, "Alan Turing", "Engineering", "101 Work Ave", 8000.0, 11000.0);

        Assertions.assertThat(employee1)
            .usingRecursiveComparison()
            .ignoringFields("id", "name", "workAddress", "netSalary", "grossSalary")
            .isEqualTo(employee2);
    }

    @Test
    public void givenEmployeesWithDifferentSalaries_whenComparingIgnoringFieldsMatchingRegexes_thenEmployeesAreEqual() {

        Employee employee1 = new Employee(1L, "Jane Smith", "Marketing", "456 Work Ave", 7000.0, 9500.0);
        Employee employee2 = new Employee(2L, "Steve Jobs", "Marketing", "456 Work Ave", 7200.0, 9800.0);

        Assertions.assertThat(employee1)
            .usingRecursiveComparison()
            .ignoringFieldsMatchingRegexes(".*id", ".*name", ".*Salary")
            .isEqualTo(employee2);
    }

    @Test
    public void givenEmployeesWithNullExpectedFields_whenComparingIgnoringExpectedNullFields_thenEmployeesAreEqual() {
        Employee expectedEmployee = new Employee(null, "Alice Johnson", null, null, null, null);
        Employee actualEmployee = new Employee(3L, "Alice Johnson", "HR", "123 Work Ave", 6500.0, 9000.0);

        Assertions.assertThat(actualEmployee)
            .usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(expectedEmployee);
    }

    @Test
    public void givenEmployees_whenComparingIgnoringFields_thenContainsEqualElements() {
        Employee actual1 = new Employee(1L, "John Doe", "Engineering", "456 Work Ave", 7500.0, 10500.0);
        Employee actual2 = new Employee(2L, "John Doe", "Engineering", "101 Work Ave", 8000.0, 12000.0);
        Employee expected1 = new Employee(3L, "John Doe", "Engineering", "Nil", 0.0, 0.0);
        Employee expected2 = new Employee(4L, "John Doe", "Engineering", "Nil", 0.0, 0.0);

        List<Employee> actual = List.of(actual1, actual2);
        List<Employee> expected = List.of(expected1, expected2);

        Assertions.assertThat(actual)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "workAddress", "netSalary", "grossSalary")
            .containsExactlyInAnyOrderElementsOf(expected);
    }
}
