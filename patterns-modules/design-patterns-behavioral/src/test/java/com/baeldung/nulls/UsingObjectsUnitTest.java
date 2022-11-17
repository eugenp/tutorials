package com.baeldung.nulls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsingObjectsUnitTest {

    private UsingObjects classUnderTest;

    @BeforeEach
    public void setup() {
        classUnderTest = new UsingObjects();
    }

    @Test
    public void whenArgIsNull_thenThrowException() {

        assertThrows(NullPointerException.class, () -> classUnderTest.accept(null));
    }

    @Test
    public void whenArgIsNonNull_thenDoesNotThrowException() {

        assertDoesNotThrow(() -> classUnderTest.accept("test    "));
    }

}