package com.baeldung.collections.sorting;

import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertTrue;

public class JavaSortingUnitTest {

    private int[] toSort;
    private int[] sortedInts;
    private int[] sortedRangeInts;
    private Employee[] employees;
    private Employee[] employeesSorted;
    private Employee[] employeesSortedByAge;
    private HashMap<Integer, String> map;

    @Before
    public void initVariables() {

        toSort = new int[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 };
        sortedInts = new int[] { 1, 5, 7, 66, 88, 89, 123, 200, 255 };
        sortedRangeInts = new int[] { 5, 1, 89, 7, 88, 200, 255, 123, 66 };

        employees = new Employee[] { new Employee("John", 23, 5000), new Employee("Steve", 26, 6000), new Employee("Frank", 33, 7000), new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), new Employee("Pearl", 33, 6000) };
        employeesSorted = new Employee[] { new Employee("Earl", 43, 10000), new Employee("Frank", 33, 70000), new Employee("Jessica", 23, 4000), new Employee("John", 23, 5000), new Employee("Pearl", 33, 4000), new Employee("Steve", 26, 6000) };
        employeesSortedByAge = new Employee[] { new Employee("John", 23, 5000), new Employee("Jessica", 23, 4000), new Employee("Steve", 26, 6000), new Employee("Frank", 33, 70000), new Employee("Pearl", 33, 4000), new Employee("Earl", 43, 10000) };

        map = new HashMap<>();
        map.put(55, "John");
        map.put(22, "Apple");
        map.put(66, "Earl");
        map.put(77, "Pearl");
        map.put(12, "George");
        map.put(6, "Rocky");

    }

    @Test
    public void givenIntArray_whenUsingSort_thenSortedArray() {
        Arrays.sort(toSort);

        assertTrue(Arrays.equals(toSort, sortedInts));
    }

    @Test
    public void givenIntegerArray_whenUsingSort_thenSortedArray() {
        Integer[] integers = ArrayUtils.toObject(toSort);
        Arrays.sort(integers, Comparator.comparingInt(a -> a));

        assertTrue(Arrays.equals(integers, ArrayUtils.toObject(sortedInts)));
    }

    @Test
    public void givenArray_whenUsingSortWithLambdas_thenSortedArray() {
        Integer[] integersToSort = ArrayUtils.toObject(toSort);
        Arrays.sort(integersToSort, Comparator.comparingInt(a -> a));

        assertTrue(Arrays.equals(integersToSort, ArrayUtils.toObject(sortedInts)));
    }

    @Test
    public void givenEmpArray_SortEmpArray_thenSortedArrayinNaturalOrder() {
        Arrays.sort(employees);

        assertTrue(Arrays.equals(employees, employeesSorted));
    }

    @Test
    public void givenIntArray_whenUsingRangeSort_thenRangeSortedArray() {
        Arrays.sort(toSort, 3, 7);

        assertTrue(Arrays.equals(toSort, sortedRangeInts));
    }

    @Test
    public void givenIntArray_whenUsingParallelSort_thenArraySorted() {
        Arrays.parallelSort(toSort);

        assertTrue(Arrays.equals(toSort, sortedInts));
    }

    @Test
    public void givenArrayObjects_whenUsingComparing_thenSortedArrayObjects() {
        List<Employee> employeesList = Arrays.asList(employees);

        employeesList.sort(Comparator.comparing(Employee::getAge));// .thenComparing(Employee::getName));

        assertTrue(Arrays.equals(employeesList.toArray(), employeesSortedByAge));
    }

    @Test
    public void givenList_whenUsingSort_thenSortedList() {
        List<Integer> toSortList = Ints.asList(toSort);
        Collections.sort(toSortList);

        assertTrue(Arrays.equals(toSortList.toArray(), ArrayUtils.toObject(sortedInts)));
    }

    @Test
    public void givenMap_whenSortingByKeys_thenSortedMap() {
        Integer[] sortedKeys = new Integer[] { 6, 12, 22, 55, 66, 77 };

        List<Map.Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Comparator.comparing(Entry::getKey));
        HashMap<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        assertTrue(Arrays.equals(sortedMap.keySet()
            .toArray(), sortedKeys));
    }

    @Test
    public void givenMap_whenSortingByValues_thenSortedMap() {
        String[] sortedValues = new String[] { "Apple", "Earl", "George", "John", "Pearl", "Rocky" };

        List<Map.Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Comparator.comparing(Entry::getValue));
        HashMap<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        assertTrue(Arrays.equals(sortedMap.values()
            .toArray(), sortedValues));
    }

    @Test
    public void givenSet_whenUsingSort_thenSortedSet() {
        HashSet<Integer> integersSet = new LinkedHashSet<>(Ints.asList(toSort));
        HashSet<Integer> descSortedIntegersSet = new LinkedHashSet<>(Arrays.asList(255, 200, 123, 89, 88, 66, 7, 5, 1));

        ArrayList<Integer> list = new ArrayList<>(integersSet);
        list.sort(Comparator.reverseOrder());
        integersSet = new LinkedHashSet<>(list);

        assertTrue(Arrays.equals(integersSet.toArray(), descSortedIntegersSet.toArray()));
    }

}
