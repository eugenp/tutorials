package com.baeldung.java9.language.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOfNullableSample {

    public static void main(String[] args) {

        List<String> collection = new ArrayList<>();

        collection.add("A");
        collection.add("B");
        collection.add("C");

        Map<String, Integer> map = new HashMap<>();

        map.put("A", 10);
        map.put("C", 30);

        collection.stream()
                .flatMap(s -> {
                    Integer temp = map.get(s);
                    return temp != null ? Stream.of(temp) : Stream.empty();
                })
                .collect(Collectors.toList());

        collection.stream()
                .flatMap(s -> Stream.ofNullable(map.get(s)))
                .collect(Collectors.toList());

    }

}
