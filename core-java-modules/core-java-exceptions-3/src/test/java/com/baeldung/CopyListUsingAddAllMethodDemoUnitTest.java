package com.baeldung;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.baeldung.CopyListUsingAddAllMethodDemo.copyList;
import static org.junit.jupiter.api.Assertions.*;

class CopyListUsingAddAllMethodDemoUnitTest {

    @Test
    void shouldMakeACopyOfSourceList() {
        List<Integer> source = Arrays.asList(11, 22, 33);

        assertEquals(source, copyList(source));
    }
}