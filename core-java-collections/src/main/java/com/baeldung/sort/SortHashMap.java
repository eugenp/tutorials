package com.baeldung.sort;

import com.baeldung.performance.Employee;

import java.util.*;

public class SortHashMap {

    private static Map<String, Employee> map = new HashMap<>();

    public static void main(String[] args) {

        initialize();

        //treeMapSortByKey();

        //arrayListSortByValue();
        //arrayListSortByKey();

        treeSetByKey();
        treeSetByValue();
    }

    private static void treeSetByValue() {
        Comparator<Employee> comp = (Employee o1, Employee o2) -> (o1.getId().compareTo(o2.getId()));
        SortedSet<Employee> values = new TreeSet<>(comp);
        values.addAll(map.values());
        System.out.println(values);
    }

    private static void treeSetByKey() {
        SortedSet<String> keys = new TreeSet<>(map.keySet());
        System.out.println(keys);
    }

    private static void treeMapSortByKey() {
        TreeMap<String, Employee> sorted = new TreeMap<>(map);
        sorted.putAll(map);

        for (Map.Entry<String, Employee> entry : sorted.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }

    }

    private static void arrayListSortByValue() {
        List<Employee> employeeById = new ArrayList<>(map.values());

        Collections.sort(employeeById, new Comparator<Employee>() {

            public int compare(Employee o1, Employee o2) {
                return (int)(o1.getId() - o2.getId());
            }
        });

        System.out.println(employeeById);
    }

    private static void arrayListSortByKey() {
        List<String> employeeByKey = new ArrayList<>(map.keySet());
        Collections.sort(employeeByKey);
        System.out.println(employeeByKey);
    }

    private static void initialize() {
        Employee employee1 = new Employee(1L, "Mher");
        map.put(employee1.getName(), employee1);
        Employee employee2 = new Employee(22L, "Annie");
        map.put(employee2.getName(), employee2);
        Employee employee3 = new Employee(8L, "John");
        map.put(employee3.getName(), employee3);
        Employee employee4 = new Employee(2L, "George");
        map.put(employee4.getName(), employee4);
    }
}
