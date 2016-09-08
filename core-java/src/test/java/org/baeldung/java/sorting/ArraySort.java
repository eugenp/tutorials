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

    @Test
    public void givenIntArray_whenUsingSort_thenSortedArray() {
        int [] numbers = new int[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 }, 
          sortedInts = {1, 5, 7, 66, 88, 89, 123, 200, 255};
        
        Arrays.sort(numbers);

        assertTrue(Arrays.equals(numbers, sortedInts));
    }

    @Test
    public void givenArrayObjects_whenUsingSort_thenSortedArrayObjects() {
        Integer[] integers = new Integer[] 
          { 5, 1, 89, 255, 7, 88, 200, 123, 66 }, 
            sortedIntegers = {1, 5, 7, 66, 88, 89, 123, 200, 255};
        
        
        Arrays.sort(integers, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });
        
        assertTrue(Arrays.equals(integers, sortedIntegers));
    }

    @Test
    public void givenArray_whenUsingSortWithLambdas_thenSortedArray() {
        Integer[] integers = new Integer[] 
            { 5, 1, 89, 255, 7, 88, 200, 123, 66 }, 
              sortedIntegers = {1, 5, 7, 66, 88, 89, 123, 200, 255};
        
        Arrays.sort(integers, (a, b) -> {
            return a - b;
        });
        
        assertTrue(Arrays.equals(integers, sortedIntegers));
    }

    @Test
    public void givenEmpArray_SortEmpArray_thenSortedArrayinNaturalOrder() {
        Employee[] employees = new Employee[] { 
          new Employee("John", 23, 5000), 
          new Employee("Steve", 26, 6000), 
          new Employee("Frank", 33, 7000),
          new Employee("Earl", 43, 10000), 
          new Employee("Jessica", 23, 4000), 
          new Employee("Pearl", 33, 6000)};
        String sortedArrayString = "[(John,23,5000.0), (Jessica,23,4000.0), "
          + "(Steve,26,6000.0), (Frank,33,7000.0), (Pearl,33,6000.0), "
          + "(Earl,43,10000.0)]";
        
        Arrays.sort(employees);

        assertTrue(Arrays.toString(employees).equals(sortedArrayString));
    }

//    @Test
//    public void comparatorSortEmployeeArrayByName() {
//        Arrays.sort(employees, new Comparator<Employee>() {
//
//            @Override
//            public int compare(Employee o1, Employee o2) {
//
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
//    }

//    @Test
//    public void comparatorSortEmployeeArrayByName_Java8Lambda() {
//        Arrays.sort(employees, (o1, o2) -> {
//            return o1.getName().compareTo(o2.getName());
//
//        });
//    }
    
    @Test
    public void givenIntArray_whenUsingRangeSort_thenRangeSortedArray() {
        int [] numbers = new int[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 }, 
            sortedRangeInts = {5, 1, 89, 7, 88, 200, 255, 123, 66};
        
        Arrays.sort(numbers, 3, 7);
        
        assertTrue(Arrays.equals(numbers, sortedRangeInts));
    }
    
    @Test 
    public void givenIntArray_whenUsingParallelSort_thenParallelSortedArray() {
        int [] numbers = new int[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 }, 
            sortedInts = {1, 5, 7, 66, 88, 89, 123, 200, 255};
        
        Arrays.parallelSort(numbers);
        
        assertTrue(Arrays.equals(numbers, sortedInts));
    }

}
