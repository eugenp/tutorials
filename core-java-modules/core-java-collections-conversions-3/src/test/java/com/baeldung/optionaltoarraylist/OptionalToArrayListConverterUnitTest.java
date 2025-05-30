package com.baeldung.optionaltoarraylist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class OptionalToArrayListConverterUnitTest {

    @Test
    public void givenOptional_whenConvertUsingIfPresent_thenReturnArrayList() {
        Optional<String> optionalValue = Optional.of("Hello, World!");
        List<String> arrayList = OptionalToArrayListConverter.usingIfPresent(optionalValue);
        assertTrue(arrayList.contains("Hello, World!"));
    }

    @Test
    public void givenNoneEmptyOptional_whenConvertUsingOrElse_thenReturnArrayList() {
        Optional<String> optionalValue = Optional.of("Hello, Baeldung!");
        List<String> arrayList = OptionalToArrayListConverter.usingOrElse(optionalValue);
        assertTrue(arrayList.contains("Hello, Baeldung!"));
    }

    @Test
    public void givenEmptyOptional_whenConvertUsingOrElse_thenReturnArrayList() {
        Optional<String> emptyOptional = Optional.empty();
        List<String> arrayList = OptionalToArrayListConverter.usingOrElse(emptyOptional);
        assertTrue(arrayList.contains("Hello, World!"));
    }

    @Test
    public void givenEmptyOptional_whenConvertUsingOrElseGet_thenReturnArrayList() {
        Optional<String> emptyOptional = Optional.empty();
        List<String> arrayList = OptionalToArrayListConverter.usingOrElseGet(emptyOptional);
        assertTrue(arrayList.contains("Hello, World!"));
    }

    @Test
    public void givenOptional_whenConvertUsingStream_thenReturnArrayList() {
        Optional<String> optionalValue = Optional.of("Hello, World!");
        List<String> arrayList = OptionalToArrayListConverter.usingStream(optionalValue);
        assertTrue(arrayList.contains("Hello, World!"));
    }

    @Test
    public void givenEmptyOptional_whenConvertUsingStream_thenReturnEmptyArrayList() {
        Optional<String> emptyOptional = Optional.empty();
        List<String> arrayList = OptionalToArrayListConverter.usingStream(emptyOptional);
        assertEquals(0, arrayList.size());
    }

    @Test
    public void givenOptional_whenConvertUsingStreamFilter_thenReturnArrayList() {
        Optional<String> optionalValue = Optional.of("Hello, World!");
        List<String> arrayList = OptionalToArrayListConverter.usingStreamFilter(optionalValue);
        assertTrue(arrayList.contains("Hello, World!"));
    }

    @Test
    public void givenNestedListOptional_whenConvertUsingStreamFlatMap_thenReturnArrayList() {
        Optional<List<String>> optionalList = Optional.of(Arrays.asList("Apple", "Banana", "Cherry"));
        List<String> arrayList = OptionalToArrayListConverter.usingStreamFlatMap(optionalList);
        assertEquals(3, arrayList.size());
        assertTrue(arrayList.contains("Apple"));
    }
}
