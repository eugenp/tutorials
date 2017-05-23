package com.baeldung.java8;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class Java8MapAndFlatMap {

    @Test
    public void givenStream_whenCalledMap_thenProduceOneResultValue() {
        List<String> myList  = Stream.of("a", "b")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertEquals(asList("A", "B"), myList );
    }

    @Test
    public void givenStream_whenCalledFlatMap_thenProduceResultValues() throws Exception {
        List<String> myList  = Stream.of(asList("a"), asList("b", "c"))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        assertEquals(asList("a", "b", "c"), myList);
    }

    @Test
    public void givenOptional_whenCalledMap_thenProduceOneResultValue() {
        Optional<String> s = Optional.of("test");
        assertEquals(Optional.of("test"), s.map(Java8MapAndFlatMap::getOutput));
    }

    static String getOutput(String input) {
        return input == null ? null : input;
    }

    @Test
    public void givenOptional_whenCalledFlatMap_thenProduceResultValues() {
        Optional<String> s = Optional.of("test");
        assertEquals(Optional.of("test"), s.flatMap(Java8MapAndFlatMap::getOutputOptional));
    }

    static Optional<String> getOutputOptional(String input) {
        return input == null ? Optional.empty() : Optional.of(input);
    }

}
