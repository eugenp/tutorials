package com.baeldung.testsuite.subpackage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassTwoUnitTest {
    @Test
    public void whenTrue_thenTrue() {
        Assertions.assertTrue(true);
    }

    @Test
    public void whenFalse_thenFalse() {
        Assertions.assertFalse(false);
    }
}
