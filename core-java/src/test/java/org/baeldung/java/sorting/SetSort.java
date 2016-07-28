package org.baeldung.java.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class SetSort {

    private HashSet<Integer> integers;
    private TreeSet<Employee> employees;

    @Before
    public void initData() {

        integers = new HashSet<>();
        integers.addAll(Arrays.asList(new Integer[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 }));

        employees = new TreeSet<>();

        employees.addAll(Arrays.asList(new Employee[] { new Employee("John", 23, 5000), new Employee("Steve", 26, 6000),
            new Employee("Frank", 33, 7000), new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), new Employee("Pearl", 33, 6000) }));

     }

    @Test
    public void hashSetSortIntegers() {

 

        ArrayList<Integer> list = new ArrayList<Integer>(integers);
        Collections.sort(list, (i1, i2) -> {
            return i2 - i1;
        });

  
    }

    @Test
    public void treeSetEmployeeSorting() {

        ArrayList<Employee> list = new ArrayList<Employee>(employees);
        Collections.sort(list, (e1, e2) -> {
            return e2.getName().compareTo(e1.getName());
        });

    }

}
