package com.baeldung.sort;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.*;
import java.util.stream.Collectors;

public class SortHashMap {

    private static Map<String, Employee> map = new HashMap<>();

    public static void main(String[] args) {

        initialize();

        treeMapSortByKey();

        arrayListSortByValue();
        arrayListSortByKey();

        sortStream();

        sortGuava();

        addDuplicates();

        treeSetByKey();
        treeSetByValue();

    }

    private static void sortGuava() {
        final Ordering naturalOrdering =
        Ordering.natural().onResultOf(Functions.forMap(map, null));

        System.out.println(ImmutableSortedMap.copyOf(map, naturalOrdering));
    }

    private static void sortStream() {
        map.entrySet().stream()
                .sorted(Map.Entry.<String, Employee>comparingByKey().reversed())
                .forEach(System.out::println);

        Map<String, Employee> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        result.entrySet().forEach(System.out::println);
    }

    private static void treeSetByValue() {
        SortedSet<Employee> values = new TreeSet<>(map.values());
        System.out.println(values);
    }

    private static void treeSetByKey() {
        SortedSet<String> keysSet = new TreeSet<>(map.keySet());
        System.out.println(keysSet);
    }

    private static void treeMapSortByKey() {
        TreeMap<String, Employee> sorted = new TreeMap<>(map);
        sorted.putAll(map);

        sorted.entrySet().forEach(System.out::println);

    }

    private static void arrayListSortByValue() {
        List<Employee> employeeById = new ArrayList<>(map.values());

        Collections.sort(employeeById);

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

    private static void addDuplicates() {
        Employee employee5 = new Employee(1L, "Mher");
        map.put(employee5.getName(), employee5);
        Employee employee6 = new Employee(22L, "Annie");
        map.put(employee6.getName(), employee6);
    }
}
