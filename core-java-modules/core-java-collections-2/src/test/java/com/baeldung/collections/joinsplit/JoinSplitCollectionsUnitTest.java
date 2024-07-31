package com.baeldung.collections.joinsplit;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class JoinSplitCollectionsUnitTest {

    @Test
    public void whenJoiningTwoArrays_thenJoined() {
        String[] animals1 = new String[] { "Dog", "Cat" };
        String[] animals2 = new String[] { "Bird", "Cow" };
        String[] result = Stream.concat(Arrays.stream(animals1), Arrays.stream(animals2)).toArray(String[]::new);

        assertArrayEquals(result, new String[] { "Dog", "Cat", "Bird", "Cow" });
    }

    @Test
    public void whenJoiningTwoCollections_thenJoined() {
        Collection<String> collection1 = Arrays.asList("Dog", "Cat");
        Collection<String> collection2 = Arrays.asList("Bird", "Cow", "Moose");
        Collection<String> result = Stream.concat(collection1.stream(), collection2.stream()).collect(Collectors.toList());

        assertTrue(result.equals(Arrays.asList("Dog", "Cat", "Bird", "Cow", "Moose")));
    }

    @Test
    public void whenJoiningTwoCollectionsWithFilter_thenJoined() {
        Collection<String> collection1 = Arrays.asList("Dog", "Cat");
        Collection<String> collection2 = Arrays.asList("Bird", "Cow", "Moose");
        Collection<String> result = Stream.concat(collection1.stream(), collection2.stream()).filter(e -> e.length() == 3).collect(Collectors.toList());

        assertTrue(result.equals(Arrays.asList("Dog", "Cat", "Cow")));
    }

    @Test
    public void whenConvertArrayToString_thenConverted() {
        String[] animals = new String[] { "Dog", "Cat", "Bird", "Cow" };
        String result = Arrays.stream(animals).collect(Collectors.joining(", "));

        assertEquals(result, "Dog, Cat, Bird, Cow");
    }

    @Test
    public void whenConvertCollectionToString_thenConverted() {
        Collection<String> animals = Arrays.asList("Dog", "Cat", "Bird", "Cow");
        String result = animals.stream().collect(Collectors.joining(", "));

        assertEquals(result, "Dog, Cat, Bird, Cow");
    }

    @Test
    public void whenConvertMapToString_thenConverted() {
        Map<Integer, String> animals = new HashMap<>();
        animals.put(1, "Dog");
        animals.put(2, "Cat");
        animals.put(3, "Cow");

        String result = animals.entrySet().stream().map(entry -> entry.getKey() + " = " + entry.getValue()).collect(Collectors.joining(", "));

        assertEquals(result, "1 = Dog, 2 = Cat, 3 = Cow");
    }

    @Test
    public void whenConvertNestedCollectionToString_thenConverted() {
        Collection<List<String>> nested = new ArrayList<>();
        nested.add(Arrays.asList("Dog", "Cat"));
        nested.add(Arrays.asList("Cow", "Pig"));

        String result = nested.stream().map(nextList -> nextList.stream().collect(Collectors.joining("-"))).collect(Collectors.joining("; "));

        assertEquals(result, "Dog-Cat; Cow-Pig");
    }

    @Test
    public void whenConvertCollectionToStringAndSkipNull_thenConverted() {
        Collection<String> animals = Arrays.asList("Dog", "Cat", null, "Moose");
        String result = animals.stream().filter(Objects::nonNull).collect(Collectors.joining(", "));

        assertEquals(result, "Dog, Cat, Moose");
    }

    @Test
    public void whenSplitCollectionHalf_thenConverted() {
        Collection<String> animals = Arrays.asList("Dog", "Cat", "Cow", "Bird", "Moose", "Pig");
        Collection<String> result1 = new ArrayList<>();
        Collection<String> result2 = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        int midpoint = Math.round(animals.size() / 2);

        animals.forEach(next -> {
            int index = count.getAndIncrement();
            if (index < midpoint) {
                result1.add(next);
            } else {
                result2.add(next);
            }
        });

        assertTrue(result1.equals(Arrays.asList("Dog", "Cat", "Cow")));
        assertTrue(result2.equals(Arrays.asList("Bird", "Moose", "Pig")));
    }

    @Test
    public void whenSplitArrayByWordLength_thenConverted() {
        String[] animals = new String[] { "Dog", "Cat", "Bird", "Cow", "Pig", "Moose" };
        Map<Integer, List<String>> result = Arrays.stream(animals).collect(Collectors.groupingBy(String::length));

        assertTrue(result.get(3).equals(Arrays.asList("Dog", "Cat", "Cow", "Pig")));
        assertTrue(result.get(4).equals(Arrays.asList("Bird")));
        assertTrue(result.get(5).equals(Arrays.asList("Moose")));
    }

    @Test
    public void whenConvertStringToArray_thenConverted() {
        String animals = "Dog, Cat, Bird, Cow";
        String[] result = animals.split(", ");

        assertArrayEquals(result, new String[] { "Dog", "Cat", "Bird", "Cow" });
    }

    @Test
    public void whenConvertStringToCollection_thenConverted() {
        String animals = "Dog, Cat, Bird, Cow";
        Collection<String> result = Arrays.asList(animals.split(", "));

        assertTrue(result.equals(Arrays.asList("Dog", "Cat", "Bird", "Cow")));
    }

    @Test
    public void whenConvertStringToMap_thenConverted() {
        String animals = "1 = Dog, 2 = Cat, 3 = Bird";

        Map<Integer, String> result = Arrays.stream(animals.split(", ")).map(next -> next.split(" = ")).collect(Collectors.toMap(entry -> Integer.parseInt(entry[0]), entry -> entry[1]));

        assertEquals(result.get(1), "Dog");
        assertEquals(result.get(2), "Cat");
        assertEquals(result.get(3), "Bird");
    }

    @Test
    public void whenConvertCollectionToStringMultipleSeparators_thenConverted() {
        String animals = "Dog. , Cat, Bird. Cow";

        Collection<String> result = Arrays.stream(animals.split("[,|.]")).map(String::trim).filter(next -> !next.isEmpty()).collect(Collectors.toList());

        assertTrue(result.equals(Arrays.asList("Dog", "Cat", "Bird", "Cow")));
    }
}
