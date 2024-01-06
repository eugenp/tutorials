package com.baeldung.iteratorvsforloop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.util.ListIterator;

public class IteratorForLoopUnitTest {

    @Test
    public void givenEmptyCollection_whenUsingForLoop_thenNoElementsAreIterated() {
        List<String> names = Collections.emptyList();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < names.size(); i++) {
            stringBuilder.append(names.get(i));
        }

        assertEquals("", stringBuilder.toString());
    }

    @Test
    public void givenEmptyCollection_whenUsingIterator_thenNoElementsAreIterated() {
        List<String> names = Collections.emptyList();
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
        }

        assertEquals("", stringBuilder.toString());
    }


    @Test
    public void givenCollectionWithElements_whenUsingForLoop_thenAllElementsAreIterated() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < names.size(); i++) {
            stringBuilder.append(names.get(i));
        }

        assertEquals("AliceBobCharlie", stringBuilder.toString());
    }

    @Test
    public void givenCollectionWithElements_whenUsingIterator_thenAllElementsAreIterated() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
        }

        assertEquals("AliceBobCharlie", stringBuilder.toString());
    }

    @Test
    public void givenCollectionWithElements_whenUsingForLoop_thenAllElementsAreIteratedReverseOrder() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = names.size() - 1; i >= 0; i--) {
            stringBuilder.append(names.get(i));
        }

        assertEquals("CharlieBobAlice", stringBuilder.toString());
    }

    @Test
    public void givenCollectionWithElements_whenUsingListIterator_thenAllElementsAreIteratedInReverseOrder() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        StringBuilder stringBuilder = new StringBuilder();

        ListIterator<String> listIterator = names.listIterator(names.size());
        while (listIterator.hasPrevious()) {
            stringBuilder.append(listIterator.previous());
        }

        assertEquals("CharlieBobAlice", stringBuilder.toString());
    }

    @Test
    public void givenCollectionWithElements_whenRemovingElementDuringForLoopIteration_thenConcurrentModificationExceptionIsThrown() {
        List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Charlie"));

        assertThrows(ConcurrentModificationException.class, () -> {
            for (String name : names) {
                names.remove("Bob");
            }
        });
    }

    @Test
    public void givenCollectionWithElements_whenRemovingElementUsingIterator_thenElementIsRemovedSafely() {
        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        Iterator<String> iterator = names.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name.equals("Bob")) {
                iterator.remove();
            }
        }
        List<String> expected = Arrays.asList("Alice", "Charlie");
        assertIterableEquals(expected, names);
    }

    @Test
    public void givenCollectionWithElements_whenModifyingElementToLowerCaseDuringForLoopIteration_thenElementsAreModifiedToLowerCase() {
        List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Charlie"));

        for (int i = 0; i < names.size(); i++) {
            names.set(i, names.get(i).toLowerCase());
        }

        List<String> expected = Arrays.asList("alice","bob", "charlie");
        assertIterableEquals(expected, names);
    }
}
