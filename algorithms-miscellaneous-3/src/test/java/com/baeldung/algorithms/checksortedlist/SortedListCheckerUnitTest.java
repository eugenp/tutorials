package com.baeldung.algorithms.checksortedlist;

import static com.baeldung.algorithms.checksortedlist.SortedListChecker.checkIfSortedUsingComparators;
import static com.baeldung.algorithms.checksortedlist.SortedListChecker.checkIfSortedUsingIterativeApproach;
import static com.baeldung.algorithms.checksortedlist.SortedListChecker.checkIfSortedUsingOrderingClass;
import static com.baeldung.algorithms.checksortedlist.SortedListChecker.checkIfSortedUsingRecursion;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SortedListCheckerUnitTest {

    private List<String> sortedListOfString;
    private List<String> unsortedListOfString;
    private List<String> singletonList;

    private List<Employee> employeesSortedByName;
    private List<Employee> employeesNotSortedByName;

    @Before
    public void setUp() {
        sortedListOfString = asList("Canada", "HK", "LA", "NJ", "NY");
        unsortedListOfString = asList("LA", "HK", "NJ", "NY", "Canada");
        singletonList = Collections.singletonList("NY");

        employeesSortedByName = asList(new Employee(1L, "John"), new Employee(2L, "Kevin"), new Employee(3L, "Mike"));
        employeesNotSortedByName = asList(new Employee(1L, "Kevin"), new Employee(2L, "John"), new Employee(3L, "Mike"));
    }

    @Test
    public void givenSortedList_whenUsingIterativeApproach_thenReturnTrue() {
        assertThat(checkIfSortedUsingIterativeApproach(sortedListOfString)).isTrue();
    }

    @Test
    public void givenSingleElementList_whenUsingIterativeApproach_thenReturnTrue() {
        assertThat(checkIfSortedUsingIterativeApproach(singletonList)).isTrue();
    }

    @Test
    public void givenUnsortedList_whenUsingIterativeApproach_thenReturnFalse() {
        assertThat(checkIfSortedUsingIterativeApproach(unsortedListOfString)).isFalse();
    }

    @Test
    public void givenSortedListOfEmployees_whenUsingIterativeApproach_thenReturnTrue() {
        assertThat(checkIfSortedUsingIterativeApproach(employeesSortedByName, Comparator.comparing(Employee::getName))).isTrue();
    }

    @Test
    public void givenUnsortedListOfEmployees_whenUsingIterativeApproach_thenReturnFalse() {
        assertThat(checkIfSortedUsingIterativeApproach(employeesNotSortedByName, Comparator.comparing(Employee::getName))).isFalse();
    }

    @Test
    public void givenSortedList_whenUsingRecursion_thenReturnTrue() {
        assertThat(checkIfSortedUsingRecursion(sortedListOfString)).isTrue();
    }

    @Test
    public void givenSingleElementList_whenUsingRecursion_thenReturnTrue() {
        assertThat(checkIfSortedUsingRecursion(singletonList)).isTrue();
    }

    @Test
    public void givenUnsortedList_whenUsingRecursion_thenReturnFalse() {
        assertThat(checkIfSortedUsingRecursion(unsortedListOfString)).isFalse();
    }

    @Test
    public void givenSortedList_whenUsingGuavaOrdering_thenReturnTrue() {
        assertThat(checkIfSortedUsingOrderingClass(sortedListOfString)).isTrue();
    }

    @Test
    public void givenUnsortedList_whenUsingGuavaOrdering_thenReturnFalse() {
        assertThat(checkIfSortedUsingOrderingClass(unsortedListOfString)).isFalse();
    }

    @Test
    public void givenSortedListOfEmployees_whenUsingGuavaOrdering_thenReturnTrue() {
        assertThat(checkIfSortedUsingOrderingClass(employeesSortedByName, Comparator.comparing(Employee::getName))).isTrue();
    }

    @Test
    public void givenUnsortedListOfEmployees_whenUsingGuavaOrdering_thenReturnFalse() {
        assertThat(checkIfSortedUsingOrderingClass(employeesNotSortedByName, Comparator.comparing(Employee::getName))).isFalse();
    }

    @Test
    public void givenSortedList_whenUsingGuavaComparators_thenReturnTrue() {
        assertThat(checkIfSortedUsingComparators(sortedListOfString)).isTrue();
    }

    @Test
    public void givenUnsortedList_whenUsingGuavaComparators_thenReturnFalse() {
        assertThat(checkIfSortedUsingComparators(unsortedListOfString)).isFalse();
    }

}
