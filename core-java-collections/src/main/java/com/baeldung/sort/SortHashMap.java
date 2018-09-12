package com.baeldung.sort;

import com.baeldung.performance.Employee;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.*;
import java.util.stream.Collectors;

public class SortHashMap {

    private static Map<String, Employee> map = new HashMap<>();

    public static void main(String[] args) {

        initialize();

        //treeMapSortByKey();

        //arrayListSortByValue();
        //arrayListSortByKey();

        //treeSetByKey();
        //treeSetByValue();

        //sortStream();

        sortGuava();
    }

    private static void sortGuava() {
        Ordering<Map.Entry<String, Employee>> orderById = new Ordering<Map.Entry<String, Employee>>() {
            @Override
            public int compare(Map.Entry<String, Employee> left, Map.Entry<String, Employee> right) {
                return left.getValue().getId().compareTo(right.getValue().getId());
            }
        };

        List<Map.Entry<String, Employee>> toList = Lists.newArrayList(map.entrySet());
        Collections.sort(toList, orderById);

        toList.forEach(System.out::println);
    }

    private static void sortStream() {
        map.entrySet().stream()
                .sorted(Map.Entry.<String, Employee>comparingByKey().reversed())
                .forEach(System.out::println);

        Map<String, Employee> result = map.entrySet().stream()
                .sorted(Comparator.comparingLong(e -> e.getValue().getId()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        result.entrySet().forEach(System.out::println);
    }

    private static void treeSetByValue() {
        Comparator<Employee> comp = (Employee o1, Employee o2) -> (o1.getId().compareTo(o2.getId()));
        SortedSet<Employee> values = new TreeSet<>(comp);
        values.addAll(map.values());
        System.out.println(values);
    }

    private static void treeSetByKey() {
        SortedSet<String> keysSet = new TreeSet<>(map.keySet());
        System.out.println(keysSet);
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
