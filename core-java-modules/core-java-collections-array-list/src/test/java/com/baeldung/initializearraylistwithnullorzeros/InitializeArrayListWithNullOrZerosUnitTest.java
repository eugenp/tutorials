package com.baeldung.initializearraylistwithnullorzeros;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InitializeArrayListWithNullOrZerosUnitTest {

    @Test
    public void whenInitializingListWithNCopies_thenListIsCorrectlyPopulated() {
        // when
        ArrayList<Integer> list = IntStream.of(new int[10])
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        // then
        Assertions.assertEquals(10, list.size());
        Assertions.assertTrue(list.stream().allMatch(elem -> elem == 0));
    }

    @Test
    public void whenInitializingListWithStream_thenListIsCorrectlyPopulated() {

        // when
        ArrayList<Integer> listWithZeros = Stream.generate(() -> 0)
                .limit(10).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Object> listWithNulls = Stream.generate(() -> null)
                .limit(10).collect(Collectors.toCollection(ArrayList::new));

        // then
        Assertions.assertEquals(10, listWithZeros.size());
        Assertions.assertTrue(listWithZeros.stream().allMatch(elem -> elem == 0));

        Assertions.assertEquals(10, listWithNulls.size());
        Assertions.assertTrue(listWithNulls.stream().allMatch(Objects::isNull));
    }

    @Test public void whenInitializingListWithIntStream_thenListIsCorrectlyPopulated() {
        // when
        ArrayList<Integer> list = IntStream.of(new int[10])
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        // then
        Assertions.assertEquals(10, list.size());
        Assertions.assertTrue(list.stream().allMatch(elem -> elem == 0)); }

    @Test
    public void whenInitializingListWithAsList_thenListIsCorrectlyPopulated() {
        // when
        Integer[] integers = new Integer[10];
        Arrays.fill(integers, 0);
        List<Integer> integerList = new ArrayList<>(Arrays.asList(integers));

        // then
        Assertions.assertEquals(10, integerList.size());
        Assertions.assertTrue(integerList.stream().allMatch(elem -> elem == 0));
    }

    @Test
    public void whenInitializingListWithVector_thenListIsCorrectlyPopulated() {
        // when
        List<Integer> integerList = new Vector<>() {{setSize(10);}};

        // then
        Assertions.assertEquals(10, integerList.size());
        Assertions.assertTrue(integerList.stream().allMatch(Objects::isNull));
    }
}
