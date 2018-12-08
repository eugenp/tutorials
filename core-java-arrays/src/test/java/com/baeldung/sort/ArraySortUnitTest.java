package com.baeldung.sort;

import com.baeldung.arraycopy.model.Employee;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArraySortUnitTest {
    private Employee[] employees;
    private int[] numbers;
    private String[] strings;

    @Before
    public void setup() {
        createEmployeesArray();
        createNumbersArray();
        createStringArray();
    }

    private void createEmployeesArray() {
        employees = new Employee[5];
        Employee employee;
        for (int i = 1; i <= employees.length; i++) {
            employee = new Employee(i, "Emp" + i);
            employees[i - 1] = employee;
        }
        Collections.shuffle(Arrays.asList(employees));
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

        Employee[] sorted = new Employee[]{
          new Employee(1, "Emp1"),
          new Employee(2, "Emp2"),
          new Employee(3, "Emp3"),
          new Employee(4, "Emp4"),
          new Employee(5, "Emp5")
        };

        assertEquals(sorted[0].getId(), employees[0].getId());
    }

    @Test
    public void givenObjectArray_whenSortingDescending_thenCorrectlySorted() {
        Arrays.sort(employees, Comparator.comparing(Employee::getName).reversed());

        Employee[] sorted = new Employee[]{
          new Employee(5, "Emp5"),
          new Employee(4, "Emp4"),
          new Employee(3, "Emp3"),
          new Employee(2, "Emp2"),
          new Employee(1, "Emp1")
        };

        assertEquals(sorted[0].getId(), employees[0].getId());
    }

    @Test
    public void givenObjectArray_whenSortingMultipleAttributesAscending_thenCorrectlySorted() {
        Arrays.sort(employees, Comparator.comparing(Employee::getName).thenComparing(Employee::getId));

        Employee[] sorted = new Employee[]{
          new Employee(1, "Emp1"),
          new Employee(2, "Emp2"),
          new Employee(3, "Emp3"),
          new Employee(4, "Emp4"),
          new Employee(5, "Emp5")
        };

        assertEquals(sorted[0].getId(), employees[0].getId());
    }

}
