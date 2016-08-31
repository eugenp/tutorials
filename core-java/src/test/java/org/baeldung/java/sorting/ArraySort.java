package org.baeldung.java.sorting;

import static org.junit.Assert.*;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ArraySort {

    private int[] numbers;
    private Integer[] integers;
    private String[] names;
    private Employee[] employees;

    @Before
    public void initData() {
        numbers = new int[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 };
        integers = new Integer[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 };

        names = new String[] { "John", "Apple", "Steve", "Frank", "Earl", "Jessica", "Jake", "Pearl" };

        employees = new Employee[] { new Employee("John", 23, 5000), new Employee("Steve", 26, 6000), new Employee("Frank", 33, 7000),
            new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), new Employee("Pearl", 33, 6000) };

    }

    @Test
    public void naturalSortIntArray() {

        Arrays.sort(numbers);

    }

    @Test
    public void comparatorSortIntArray() {

        Arrays.sort(integers, new Comparator<Integer>() {

            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

    }

    @Test
    public void givenArray_whenUsingSortWithLambdas_thenSortedArray() {
        Arrays.sort(integers, (a, b) -> {
            return a - b;
        });

    }

    @Test
    public void comparableSortEmployeeArrayByAge_NaturalOrder() {

        Arrays.sort(employees);

    }

    @Test
    public void comparatorSortEmployeeArrayByName() {
        Arrays.sort(employees, new Comparator<Employee>() {

            @Override
            public int compare(Employee o1, Employee o2) {

                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    @Test
    public void comparatorSortEmployeeArrayByName_Java8Lambda() {
        Arrays.sort(employees, (o1, o2) -> {
            return o1.getName().compareTo(o2.getName());

        });
    }
    
    @Test
    public void givenIntArray_whenUsingRangeSort_thenRangeSortedArray() {
        System.out.println(Arrays.toString(numbers));
        Arrays.sort(numbers, 3, 7);
        System.out.println(Arrays.toString(numbers));
        
    }
    
    @Test 
    public void givenIntArray_whenUsingParallelSort_thenParallelSortedArray() {
        
        Arrays.parallelSort(numbers);
        
    }

}
