package com.baeldung.sort;

import com.baeldung.performance.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SortHashMap {

    private static Map<String, Employee> map = new HashMap<>();

    public static void main(String[] args) {

        initialize();

        treeMapSortByKey();
    }

    public static void treeMapSortByKey()
    {
        TreeMap<String, Employee> sorted = new TreeMap<>(map);
        sorted.putAll(map);

        for (Map.Entry<String, Employee> entry : sorted.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }

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
