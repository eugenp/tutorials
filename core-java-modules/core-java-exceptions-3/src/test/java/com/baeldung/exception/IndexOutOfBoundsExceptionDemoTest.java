package com.baeldung.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.baeldung.exception.IndexOutOfBoundsExceptionDemo.copyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class IndexOutOfBoundsExceptionDemoTest {

    @Test
    void shouldReturnCopyOfSourceList() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        assertThrows(IndexOutOfBoundsException.class, () -> copyList(source));
    }

    @Test
    void shouldCopyEmptyList() {
        List<Integer> source = Collections.emptyList();

        assertEquals(source, copyList(source));
    }
}