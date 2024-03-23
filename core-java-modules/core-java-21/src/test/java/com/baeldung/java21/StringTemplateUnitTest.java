package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class StringTemplateUnitTest {

    @Test
    void whenNoSwitchPattern_thenReturnSavingsAccountBalance() {
        StringTemplates stringTemplates = new StringTemplates();
        assertEquals("Welcome to Baeldung", stringTemplates.getStringTemplate());
    }
}
