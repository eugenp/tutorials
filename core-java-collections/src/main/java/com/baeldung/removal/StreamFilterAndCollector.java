package com.baeldung.removal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StreamFilterAndCollector {

    public static void main(String args[]) {
        Collection<String> names = new ArrayList<>();
        names.add("John");
        names.add("Ana");
        names.add("Mary");
        names.add("Anthony");
        names.add("Mark");

        Collection<String> filteredCollection = names
                .stream()
                .filter(e -> !e.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println(String.join(",", filteredCollection));
    }
}
