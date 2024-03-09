package com.baeldung.listiteration;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ListIterationUnitTest {

    List<String> programmingLanguages = new ArrayList<>(List.of("Java", "Python", "C++"));
    List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3));

    @Test
    public void givenList_whenAddElementWithListIterator_thenModifiedList() {
        ListIterator<String> listIterator = programmingLanguages.listIterator();
        while (listIterator.hasNext()) {
            String language = listIterator.next();
            if (language.equals("Python")) {
                listIterator.add("JavaScript");
            }
        }

        assertIterableEquals(Arrays.asList("Java", "Python", "JavaScript", "C++"), programmingLanguages);
    }

    @Test
    public void givenList_whenAddElementWithEnhancedForLoopAndCopy_thenModifiedList() {

        List<Integer> copyOfNumbers = new ArrayList<>(numbers);
        for (int num : copyOfNumbers) {
            numbers.add(num * 2);
        }

        assertIterableEquals(Arrays.asList(1, 2, 3, 2, 4, 6), numbers);
    }

    @Test
    public void givenList_whenAddElementWithJava8Stream_thenModifiedList() {
        programmingLanguages = Stream.concat(programmingLanguages.stream(), Stream.of("JavaScript"))
                .collect(Collectors.toList());

        assertIterableEquals(Arrays.asList("Java", "Python", "C++", "JavaScript"), programmingLanguages);
    }

}
