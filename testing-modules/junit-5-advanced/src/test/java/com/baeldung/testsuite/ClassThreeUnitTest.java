package com.baeldung.testsuite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ClassThreeUnitTest {
    @Test
    @Tag("slow")
    public void whenTrue_thenTrue() {
        Assertions.assertTrue(true);
    }
}
