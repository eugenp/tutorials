package com.baeldung.gradle.secondmodule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleBTest {

    @Test
    void givenString_whenCheckLength_thenCorrect() {
        String word = "Hello World";
        assertTrue(word.length() > 3);
    }
}
