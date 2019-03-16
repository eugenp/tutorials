package com.baeldung.nulls;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsingObjectsTest {

    private UsingObjects classUnderTest;

    @BeforeEach
    public void setup() {
        classUnderTest = new UsingObjects();
    }

    @Test
    public void whenArgIsNull_thenThrowException() {

        assertThrows(Exception.class, () -> classUnderTest.accept(null));
    }

    @Test
    public void whenArgIsNonNull_thenDoesNotThrowException() {

        assertDoesNotThrow(() -> classUnderTest.accept("test    "));
    }

}