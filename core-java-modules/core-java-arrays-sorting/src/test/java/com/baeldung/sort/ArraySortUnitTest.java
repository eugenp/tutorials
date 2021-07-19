package com.baeldung.sort;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;

public class ArraySortUnitTest {
    private Employee[] employees;
    private int[] numbers;
    private String[] strings;

    private Employee john = new Employee(6, "John");
    private Employee mary = new Employee(3, "Mary");
    private Employee david = new Employee(4, "David");

    @Before
    public void setup() {
        createEmployeesArray();
        createNumbersArray();
        createStringArray();
    }

    private void createEmployeesArray() {
        employees = new Employee[]{john, mary, david};
    }

    private void createNumbersArray() {
        numbers = new int[]{-8, 7, 5, 9, 10, -2, 3};
    }

    private void createStringArray() {
        strings = new String[]{"learning", "java", "with", "baeldung"};
    }

    @Test
    public void givenIntArray_whenSortingAscending_thenCorrectlySorted() {
        Arrays.sort(numbers);

        assertArrayEquals(new int[]{-8, -2, 3, 5, 7, 9, 10}, numbers);
    }

    @Test
    public void givenIntArray_whenSortingDescending_thenCorrectlySorted() {
        numbers = IntStream.of(numbers).boxed().sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();

        assertArrayEquals(new int[]{10, 9, 7, 5, 3, -2, -8}, numbers);
    }

    @Test
    public void givenStringArray_whenSortingAscending_thenCorrectlySorted() {
        Arrays.sort(strings);

        assertArrayEquals(new String[]{"baeldung", "java", "learning", "with"}, strings);
    }

    @Test
    public void givenStringArray_whenSortingDescending_thenCorrectlySorted() {
        Arrays.sort(strings, Comparator.reverseOrder());

        assertArrayEquals(new String[]{"with", "learning", "java", "baeldung"}, strings);
    }

    @Test
    public void givenObjectArray_whenSortingAscending_thenCorrectlySorted() {
        Arrays.sort(employees, Comparator.comparing(Employee::getName));

        assertArrayEquals(new Employee[]{david, john, mary}, employees);
    }

    @Test
    public void givenObjectArray_whenSortingDescending_thenCorrectlySorted() {
        Arrays.sort(employees, Comparator.comparing(Employee::getName).reversed());

        assertArrayEquals(new Employee[]{mary, john, david}, employees);
    }

    @Test
    public void givenObjectArray_whenSortingMultipleAttributesAscending_thenCorrectlySorted() {
        Arrays.sort(employees, Comparator.comparing(Employee::getName).thenComparing(Employee::getId));

        assertArrayEquals(new Employee[]{david, john, mary}, employees);
    }
}
