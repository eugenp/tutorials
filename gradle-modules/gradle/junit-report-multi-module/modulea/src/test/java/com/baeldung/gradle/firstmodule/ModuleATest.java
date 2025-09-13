package com.baeldung.gradle.firstmodule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModuleATest {

    @Test
    void givenNumbers_whenAdd_thenCorrect() {
        int sum = 2 + 3;
        assertEquals(5, sum);
    }
}
