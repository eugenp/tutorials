package com.baeldung.threadsafe;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronizedSingletonUnitTest {
    @Test
     void givenMultipleThreads_whenUsingSynchronizedSingleton_thenOnlyOneInstanceCreated() {
        Set<Object> instances = ConcurrentHashMap.newKeySet();
        IntStream.range(0, 100).parallel().forEach(i -> instances.add(SynchronizedSingleton.getInstance()));
        assertEquals(1, instances.size());
    }
}
