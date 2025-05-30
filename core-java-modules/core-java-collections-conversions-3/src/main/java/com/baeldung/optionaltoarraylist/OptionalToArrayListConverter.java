package com.baeldung.optionaltoarraylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalToArrayListConverter {

    public static List<String> usingIfPresent(Optional<String> obj) {
        List<String> arrayList = new ArrayList<>();
        obj.ifPresent(arrayList::add);
        return arrayList;
    }

    public static List<String> usingOrElse(Optional<String> obj) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(obj.orElse("Hello, World!"));
        return arrayList;
    }

    public static List<String> usingOrElseGet(Optional<String> obj) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(obj.orElseGet(() -> "Hello, World!"));
        return arrayList;
    }

    public static List<String> usingStream(Optional<String> obj) {
        List<String> arrayList = obj.stream()
            .collect(Collectors.toList());
        return arrayList;
    }

    public static List<String> usingStreamFilter(Optional<String> obj) {
        List<String> arrayList = obj.filter(e -> e.startsWith("H"))
            .stream()
            .collect(Collectors.toList());
        return arrayList;
    }

    public static List<String> usingStreamFlatMap(Optional<List<String>> obj) {
        List<String> arrayList = obj.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
        return arrayList;
    }
}
