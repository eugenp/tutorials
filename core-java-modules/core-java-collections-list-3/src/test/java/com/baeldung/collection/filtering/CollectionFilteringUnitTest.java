package com.baeldung.collection.filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Various filtering examples.
 *
 * @author Rodolfo Felipe
 */
public class CollectionFilteringUnitTest {

    private List<Employee> buildEmployeeList() {
        return Arrays.asList(new Employee(1, "Mike", 1), new Employee(2, "John", 1), new Employee(3, "Mary", 1), new Employee(4, "Joe", 2), new Employee(5, "Nicole", 2), new Employee(6, "Alice", 2), new Employee(7, "Bob", 3), new Employee(8, "Scarlett", 3));
    }

    private List<String> employeeNameFilter() {
        return Arrays.asList("Alice", "Mike", "Bob");
    }

    @Test
    public void givenEmployeeList_andNameFilterList_thenObtainFilteredEmployeeList_usingForEachLoop() {
        List<Employee> filteredList = new ArrayList<>();
        List<Employee> originalList = buildEmployeeList();
        List<String> nameFilter = employeeNameFilter();

        for (Employee employee : originalList) {
            for (String name : nameFilter) {
                if (employee.getName().equals(name)) {
                    filteredList.add(employee);
                    //break;
                }
            }
        }

        Assert.assertThat(filteredList.size(), Matchers.is(nameFilter.size()));
    }

    @Test
    public void givenEmployeeList_andNameFilterList_thenObtainFilteredEmployeeList_usingLambda() {
        List<Employee> filteredList;
        List<Employee> originalList = buildEmployeeList();
        List<String> nameFilter = employeeNameFilter();

        filteredList = originalList.stream()
            .filter(employee -> nameFilter.contains(employee.getName()))
            .collect(Collectors.toList());

        Assert.assertThat(filteredList.size(), Matchers.is(nameFilter.size()));
    }

    @Test
    public void givenEmployeeList_andNameFilterList_thenObtainFilteredEmployeeList_usingLambdaAndHashSet() {
        List<Employee> filteredList;
        List<Employee> originalList = buildEmployeeList();
        Set<String> nameFilterSet = employeeNameFilter().stream()
            .collect(Collectors.toSet());

        filteredList = originalList.stream()
            .filter(employee -> nameFilterSet.contains(employee.getName()))
            .collect(Collectors.toList());

        Assert.assertThat(filteredList.size(), Matchers.is(nameFilterSet.size()));
    }

}
