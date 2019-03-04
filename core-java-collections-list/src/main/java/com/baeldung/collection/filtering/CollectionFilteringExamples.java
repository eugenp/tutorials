package com.baeldung.collection.filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Various filtering examples.
 *
 * @author Rodolfo Felipe
 */
public class CollectionFilteringExamples {

    private List<Employee> buildEmployeeList() {
        return Arrays.asList(new Employee(1, "Mike", 1), new Employee(2, "John", 1), new Employee(3, "Mary", 1), new Employee(4, "Joe", 2), new Employee(5, "Nicole", 2), new Employee(6, "Alice", 2), new Employee(7, "Bob", 3), new Employee(8, "Scarlett", 3));
    }

    private List<String> employeeNameFilter() {
        return Arrays.asList("Alice", "Mike", "Bob");
    }

    private List<Employee> getFilteredEmployeeList() {
        List<Employee> filteredList = new ArrayList<>();
        for (Employee employee : buildEmployeeList()) {
            for (String name : employeeNameFilter()) {
                if (employee.getName()
                    .equalsIgnoreCase(name)) {
                    filteredList.add(employee);
                }
            }
        }
        return filteredList;
    }

    private List<Employee> getFilteredEmployeeListLambdaExample() {
        return buildEmployeeList().stream()
            .filter(employee -> employeeNameFilter().contains(employee.getName()))
            .collect(Collectors.toList());
    }

    private List<Employee> getFilteredEmployeeListLambdaExampleWithHashSetContains() {
        Set<String> nameFilterSet = employeeNameFilter().stream()
            .collect(Collectors.toSet());
        return buildEmployeeList().stream()
            .filter(employee -> nameFilterSet.contains(employee.getName()))
            .collect(Collectors.toList());
    }

}
