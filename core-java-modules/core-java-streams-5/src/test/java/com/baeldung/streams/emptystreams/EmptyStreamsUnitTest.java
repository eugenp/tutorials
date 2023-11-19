package com.baeldung.streams.emptystreams;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class EmptyStreamsUnitTest {

    @Test
    public void givenEmptyStreams_findAnyReturnsAnEmptyOptional() {

        Stream<String> emptyStream = Stream.empty();
        assertTrue(emptyStream.findAny().isEmpty());

        emptyStream = Stream.of();
        assertTrue(emptyStream.findAny().isEmpty());

        IntStream emptyIntStream = IntStream.of(new int[] {});
        assertTrue(emptyIntStream.findAny().isEmpty());

        emptyIntStream = Arrays.stream(new int[] {});
        assertTrue(emptyIntStream.findAny().isEmpty());

        Stream<Integer> collectionStream = new ArrayList<Integer>().stream();
        assertTrue(collectionStream.findAny().isEmpty());
    }

    @Test
    public void givenAStreamToSupplier_NewInstanceOfTheStreamIsReturnedForEveryGetCall() {
        Supplier<Stream<Integer>> streamSupplier = () -> Stream.of(1, 2, 3, 4, 5).filter(number -> number > 5);

        Optional<Integer> result1 = streamSupplier.get().findAny();
        assertTrue(result1.isEmpty());
        Optional<Integer> result2 = streamSupplier.get().findFirst();
        assertTrue(result2.isEmpty());
    }
}
