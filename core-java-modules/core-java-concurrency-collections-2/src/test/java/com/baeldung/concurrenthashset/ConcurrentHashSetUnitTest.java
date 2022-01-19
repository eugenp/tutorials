package com.baeldung.concurrenthashset;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.junit.jupiter.api.Test;

public class ConcurrentHashSetUnitTest {

    @Test
    public void whenCreateConcurrentHashSetWithStaticMethod_thenSetIsCreated() {
        // when
        Set<Integer> threadSafeUniqueNumbers = ConcurrentHashMap.newKeySet();
        threadSafeUniqueNumbers.add(23);
        threadSafeUniqueNumbers.add(45);

        //then
        assertTrue(threadSafeUniqueNumbers.stream()
            .anyMatch(entry -> entry.equals(23)));
        assertTrue(threadSafeUniqueNumbers.stream()
            .anyMatch(entry -> entry.equals(45)));
    }

    @Test
    public void whenCreateConcurrentHashSetWithKeySetMethod_thenSetIsSyncedWithMapped() {
        // when
        ConcurrentHashMap<Integer, String> numbersMap = new ConcurrentHashMap<>();
        Set<Integer> numbersSet = numbersMap.keySet();

        numbersMap.put(1, "One");
        numbersMap.put(2, "Two");
        numbersMap.put(3, "Three");

        System.out.println("Map before remove: " + numbersMap);
        System.out.println("Set before remove: " + numbersSet);

        numbersSet.remove(2);

        System.out.println("Set after remove: " + numbersSet);
        System.out.println("Map after remove: " + numbersMap);

        //then
        assertNull(numbersMap.get(2));
    }

    @Test
    public void whenCreateConcurrentHashSetWithKeySetMethodDefaultValue_thenSetIsSyncedWithMapped() {
        // when
        ConcurrentHashMap<Integer, String> numbersMap = new ConcurrentHashMap<>();
        Set<Integer> numbersSet = numbersMap.keySet("SET-ENTRY");

        numbersMap.put(1, "One");
        numbersMap.put(2, "Two");
        numbersMap.put(3, "Three");

        System.out.println("Map before add: " + numbersMap);
        System.out.println("Set before add: " + numbersSet);

        numbersSet.addAll(asList(4, 5));

        System.out.println("Map after add: " + numbersMap);
        System.out.println("Set after add: " + numbersSet);

        //then
        assertEquals("One", numbersMap.get(1));
        assertEquals("SET-ENTRY", numbersMap.get(4));
        assertEquals("SET-ENTRY", numbersMap.get(5));
    }

    @Test
    public void whenSynchronizedSetIsCreated_thenSetIsCreated() {
        // when
        Set<Integer> syncNumbers = Collections.synchronizedSet(new HashSet<>());
        syncNumbers.add(1);

        //then
        assertTrue(syncNumbers.stream()
            .anyMatch(entry -> entry.equals(1)));
    }

    @Test
    public void whenCopyOnWriteArraySetIsCreated_thenSetIsCreated() {
        // when
        Set<Integer> copyOnArraySet = new CopyOnWriteArraySet<>();
        copyOnArraySet.add(1);

        //then
        assertTrue(copyOnArraySet.stream()
            .anyMatch(entry -> entry.equals(1)));
    }

}
