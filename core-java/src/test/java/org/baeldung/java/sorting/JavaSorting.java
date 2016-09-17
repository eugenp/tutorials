package org.baeldung.java.sorting;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class JavaSorting {
    
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
    
    

    @Test
    public void givenArrayObjects_whenUsingComparing_thenSortedArrayObjects() {
        List<Employee> employees = Arrays.asList(new Employee[] { new Employee("John", 23, 5000), 
          new Employee("Steve", 26, 6000), new Employee("Frank", 33, 7000),
          new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), 
          new Employee("Pearl", 33, 6000) });
        String sortedArrayString = "[(John,23,5000.0), (Jessica,23,4000.0), "
            + "(Steve,26,6000.0), (Frank,33,7000.0), (Pearl,33,6000.0), "
            + "(Earl,43,10000.0)]";

        employees.sort(Comparator.comparing(Employee::getAge));//.thenComparing(Employee::getName));

        assertTrue(Arrays.toString(employees.toArray()).equals(sortedArrayString));
    }
    
    @Test
    public void givenList_whenUsingSort_thenSortedList() {
        List<Integer> integers = Arrays.asList(new Integer[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 }), 
          sortedIntegers = Arrays.asList(new Integer[] {1, 5, 7, 66, 88, 89, 123, 200, 255});

        Collections.sort(integers);

        assertTrue(Arrays.equals(integers.toArray(), sortedIntegers.toArray()));
    }

    @Test
    public void givenMap_whenSortingByKeys_thenSortedMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(55, "John");
        map.put(22, "Apple");
        map.put(66, "Earl");
        map.put(77, "Pearl");
        map.put(12, "George");
        map.put(6, "Rocky");
        Integer[] sortedKeys = new Integer[] { 6, 12, 22, 55, 66, 77 };

        List<Map.Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Entry<Integer, String>>() {
            @Override
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        HashMap<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        assertTrue(Arrays.equals(sortedMap.keySet().toArray(), sortedKeys));
    }

    @Test
    public void givenMap_whenSortingByValues_thenSortedMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(55, "John");
        map.put(22, "Apple");
        map.put(66, "Earl");
        map.put(77, "Pearl");
        map.put(12, "George");
        map.put(6, "Rocky");
        String[] sortedValues = new String[] 
          { "Apple", "Earl", "George", "John", "Pearl", "Rocky" };

        List<Map.Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Entry<Integer, String>>() {
            @Override
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        HashMap<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        assertTrue(Arrays.equals(sortedMap.values().toArray(), sortedValues));
    }



    // public void showMap(Map<Integer, String> map1) {
    // for (Map.Entry<Integer, String> entry : map1.entrySet()) {
    // System.out.println("[Key: " + entry.getKey() + " , " + "Value: " + entry.getValue() + "] ");
    //
    // }
    //
    // }
    


    @Test
    public void givenSet_whenUsingSort_thenSortedSet() {
        HashSet<Integer> integers = new HashSet<>(Arrays.asList(new Integer[] 
            { 5, 1, 89, 255, 7, 88, 200, 123, 66 })),
              sortedIntegers = new HashSet<>(Arrays.asList(new Integer[] 
                {1, 5, 7, 66, 88, 89, 123, 200, 255}));
        
        
        ArrayList<Integer> list = new ArrayList<Integer>(integers);
        Collections.sort(list, (i1, i2) -> {
            return i2 - i1;
        });
        integers = new HashSet<>(list);
        
        assertTrue(Arrays.equals(integers.toArray(), sortedIntegers.toArray()));
    }



}
