package com.baeldung;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.baeldung.CopyListUsingConstructorDemo.copyList;
import static org.junit.jupiter.api.Assertions.*;

class CopyListUsingConstructorDemoUnitTest {

    @Test
    void shouldMakeACopyOfSourceListUsingArrayListConstructor() {
        List<Integer> source = Arrays.asList(11, 22, 33);

        assertEquals(source, copyList(source));
    }
}