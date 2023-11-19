package com.baeldung.streams.emptystreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EmptyStreams {

    public static void main(String[] args) {

        createEmptyStreams();
        checkForEmptyStreamUsingSupplier();
    }

    private static void createEmptyStreams() {
        
        // Using Stream.empty()
        Stream<String> emptyStream = Stream.empty();
        System.out.println(emptyStream.findAny().isEmpty());
        
        // Using Stream.of()
        emptyStream = Stream.of();
        System.out.println(emptyStream.findAny().isEmpty());

        // Empty Stream of primitive type
        IntStream emptyIntStream = IntStream.of(new int[] {});
        System.out.println(emptyIntStream.findAny().isEmpty());
        
        // Using Arrays.stream()
        emptyIntStream = Arrays.stream(new int[] {});
        System.out.println(emptyIntStream.findAny().isEmpty());

        // Using list.stream()
        Stream<Integer> collectionStream = new ArrayList<Integer>().stream();
        System.out.println(collectionStream.findAny().isEmpty());
    }
     
    private static void checkForEmptyStreamUsingSupplier() {
        Supplier<Stream<Integer>> streamSupplier = () -> Stream.of(1, 2, 3, 4, 5).filter(number -> number > 5);

        Optional<Integer> result1 = streamSupplier.get().findAny();
        System.out.println(result1.isEmpty());
        Optional<Integer> result2 = streamSupplier.get().findFirst();
        System.out.println(result2.isEmpty());
    }
}
