package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ExceptionUnitTest {

    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }

    @Test
    void assertThrowsException() {
        String str = null;
        assertThrows(IllegalArgumentException.class, () -> {
            Integer.valueOf(str);
        });
    }

    @Test
    public void whenModifyMapDuringIteration_thenThrowExecption() {
        Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(1, "One");
        hashmap.put(2, "Two");

        Executable executable = () -> hashmap.forEach((key, value) -> hashmap.remove(1));
        assertThrows(ConcurrentModificationException.class, executable);
    }
}
