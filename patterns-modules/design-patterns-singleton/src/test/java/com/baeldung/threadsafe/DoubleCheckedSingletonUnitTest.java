package com.baeldung.threadsafe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleCheckedSingletonUnitTest {
    @Test
    void givenDCLSingleton_whenAccessedFromThreads_thenOneInstanceCreated() {
        List<Object> instances = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0, 100).parallel().forEach(i -> instances.add(DoubleCheckedSingleton.getInstance()));
        assertEquals(1, new HashSet<>(instances).size());
    }
}
