package com.baeldung;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionUnitTest {

    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });

        assertEquals("Not supported", exception.getMessage());
    }

    @Test
    void assertThrowsException() {
        String str = null;
        assertThrows(IllegalArgumentException.class, () -> Integer.valueOf(str));
    }

    @Test
    void whenModifyMapDuringIteration_thenThrowExecption() {
        Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(1, "One");
        hashmap.put(2, "Two");

        Executable executable = () -> hashmap.forEach((key, value) -> hashmap.remove(1));
        assertThrows(ConcurrentModificationException.class, executable);
    }
}
