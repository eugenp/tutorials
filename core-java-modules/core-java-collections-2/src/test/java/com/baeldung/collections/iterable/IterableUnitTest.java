package com.baeldung.collections.iterable;

import com.baeldung.collections.iterable.IterableExample;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IterableUnitTest {

    private static List<Integer> getNumbers() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        return numbers;
    }

    @Test
    void givenNumbers_whenUsingIterator_thenCorrectSize() {
        List<Integer> numbers = getNumbers();
        IterableExample iterableExample = new IterableExample();
        iterableExample.iterateUsingIterator(numbers);
        assertEquals(4, numbers.size());
    }

    @Test
    void givenNumbers_whenRemoveElements_thenEmptyList() {
        List<Integer> numbers = getNumbers();
        IterableExample iterableExample = new IterableExample();
        iterableExample.removeElementsUsingIterator(numbers);
        assertEquals(0, numbers.size());
    }

    @Test
    void givenNumbers_whenIterateUsingEnhancedForLoop_thenCorrectSize() {
        List<Integer> numbers = getNumbers();
        IterableExample iterableExample = new IterableExample();
        iterableExample.iterateUsingEnhancedForLoop(numbers);
        assertEquals(4, numbers.size());
    }

    @Test
    void givenNumbers_whenIterateUsingForEachLoop_thenCorrectSize() {
        List<Integer> numbers = getNumbers();
        IterableExample iterableExample = new IterableExample();
        iterableExample.iterateUsingForEachLoop(numbers);
        assertEquals(4, numbers.size());
    }
}
