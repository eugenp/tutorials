package com.baeldung.compare;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ComparatorsUnitTest {
    @Test
    public void testCompareEmployeesFirstNameThenAge() {
        Employee employee1 = new Employee("John", 21);
        Employee employee2 = new Employee("Tom", 20);
        // Another employee named John
        Employee employee3 = new Employee("John", 22);

        List<Comparator<Employee>> comparators =
          Arrays.asList(new CheckFieldsOneByOne(),
            new ComparisonChainExample(),
            new CompareToBuilderExample(),
            ComparatorLambda.createEmployeeComparator());
        // All comparators should produce the same result
        for(Comparator<Employee> comparator : comparators) {
            Assertions.assertIterableEquals(
              Arrays.asList(employee1, employee2, employee3)
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList()),
              Arrays.asList(employee1, employee3, employee2));
        }
    }
}
