package com.baeldung.map.mergemaps;

import one.util.streamex.EntryStream;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MergeMaps {

    private static Map<String, Employee> map1 = new HashMap<>();
    private static Map<String, Employee> map2 = new HashMap<>();

    public static void main(String[] args) {

        initialize();

        mergeFunction();

        streamConcat();

        streamOf();

        streamEx();

        streamMerge();
    }

    private static void streamMerge() {

        Map<String, Employee> map3 = map2.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (v1, v2) -> new Employee(v1.getId(), v2.getName()),
                                () -> new HashMap<>(map1)
                        )
                );

        System.out.println(map3);
    }

    private static void streamEx() {
        Map<String, Employee> map3 = EntryStream.of(map1)
                .append(EntryStream.of(map2))
                .toMap((e1, e2) -> e1);

        System.out.println(map3);

    }

    private static void streamOf() {
        Map<String, Employee> map3 = Stream.of(map1, map2)
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (v1, v2) -> new Employee(v1.getId(), v2.getName())
                        )
                );

        map3.entrySet().forEach(System.out::println);
    }

    private static void streamConcat() {
        Map<String, Employee> result = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (value1, value2) -> new Employee(value2.getId(), value1.getName())
        ));

        result.entrySet().forEach(System.out::println);
    }

    private static void mergeFunction() {
        Map<String, Employee> map3 = new HashMap<>(map1);

        map2.forEach(
                (key, value) -> map3.merge(key, value, (v1, v2) ->
                        new Employee(v1.getId(), v2.getName()))
        );

        map3.entrySet().forEach(System.out::println);
    }


    private static void initialize() {
        Employee employee1 = new Employee(1L, "Henry");
        map1.put(employee1.getName(), employee1);
        Employee employee2 = new Employee(22L, "Annie");
        map1.put(employee2.getName(), employee2);
        Employee employee3 = new Employee(8L, "John");
        map1.put(employee3.getName(), employee3);

        Employee employee4 = new Employee(2L, "George");
        map2.put(employee4.getName(), employee4);
        Employee employee5 = new Employee(3L, "Henry");
        map2.put(employee5.getName(), employee5);
    }

}
