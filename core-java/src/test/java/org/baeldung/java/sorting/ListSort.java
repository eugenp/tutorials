package org.baeldung.java.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ListSort {

    private List<Integer> integers;
    private List<Employee> employees;

    @Before
    public void initData() {
        integers = Arrays.asList(new Integer[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 });

        employees = Arrays.asList(new Employee[] { new Employee("John", 23, 5000), new Employee("Steve", 26, 6000), new Employee("Frank", 33, 7000),
            new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), new Employee("Pearl", 33, 6000) });
    }

    @Test
    public void naturalOrderIntegerListSort() {

        Collections.sort(integers);

    }

    @Test
    public void comparableEmployeeSortByAge() {

        Collections.sort(employees);

    }

    @Test
    public void comparatorEmployeeSortByName() {

        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getName().compareTo(e2.getName());
            }
        });

    }

    @Test
    public void comparatorEmployeeSortByNameJava8() {

        Collections.sort(employees, (e1, e2) -> {
            return e1.getName().compareTo(e1.getName());
        });

    }

}
