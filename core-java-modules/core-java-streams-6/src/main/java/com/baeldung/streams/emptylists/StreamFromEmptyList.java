package com.baeldung.streams.emptylists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFromEmptyList {

    public static void main(String[] args) {
        createStreamFromEmptyList();
       
        List<String> nameList = getList();
        printNameLengths(nameList); // passing null
        printNameLengths((nameList == null) ? List.of() : nameList); // passing empty list
        
        collectStreamOfEmptyListToAnotherList();
    }

    private static void collectStreamOfEmptyListToAnotherList() {
        List<String> emptyList = new ArrayList<>();
        List<String> collectedList = emptyList.stream().collect(Collectors.toList());

        System.out.println(collectedList.size() == 0);

        collectedList = emptyList.stream().filter(s -> s.startsWith("a")).collect(Collectors.toList());
        System.out.println(collectedList.size() == 0);
    }

    private static void createStreamFromEmptyList() {
        List<String> emptyList = new ArrayList<>();
        Stream<String> emptyStream = emptyList.stream();
        System.out.println(emptyStream.findAny().isEmpty());
    }

    private static void printNameLengths(List<String> nameList) {
        // Without Stream - Traditional approach with null check
        if (nameList != null) {
            for (String name : nameList) {
                System.out.println("Length of " + name + ": " + name.length());
            }
        } else {
            System.out.println("List is null. Unable to process.");
        }

        // With Stream - More concise approach
        Optional.ofNullable(nameList).ifPresent(list -> list.stream()
                .map(name -> "Length of " + name + ": " + name.length()).forEach(System.out::println));
    }

    private static List<String> getList() {
        // This method may return null for demonstration purposes
        return null;
    }
}
