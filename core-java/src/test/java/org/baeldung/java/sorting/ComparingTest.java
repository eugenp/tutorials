package org.baeldung.java.sorting;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ComparingTest {

    private List<Employee> employees;

    @Before
    public void initData() {

        // employees = Arrays.asList(new Employee[] {
        // new Employee("John", 23, 5000),
        // new Employee("Steve", 26, 6000),
        // new Employee("Frank", 33, 7000),
        // new Employee("Earl", 43, 10000),
        // new Employee("Jessica", 23, 4000),
        // new Employee("Pearl", 33, 6000) });

    }

    @Test
    public void testComparing() {
        employees = Arrays.asList(new Employee[] { new Employee("John", 23, 5000), new Employee("Steve", 26, 6000), new Employee("Frank", 33, 7000),
            new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), new Employee("Pearl", 33, 6000) });

        employees.sort(Comparator.comparing(Employee::getAge).thenComparing(Employee::getName));

    }

}
