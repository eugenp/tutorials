package com.baeldung.removal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamPartitioningBy {

    public static void main(String args[]) {
        Collection<String> names = new ArrayList<>();
        names.add("John");
        names.add("Ana");
        names.add("Mary");
        names.add("Anthony");
        names.add("Mark");

        Map<Boolean, List<String>> classifiedElements = names
                .stream()
                .collect(Collectors.partitioningBy((String e) -> !e.startsWith("A")));

        String matching = String.join(",", classifiedElements.get(Boolean.TRUE));
        String nonMatching = String.join(",", classifiedElements.get(Boolean.FALSE));
        System.out.println("Matching elements: " + matching);
        System.out.println("Non matching elements: " + nonMatching);
    }
}
