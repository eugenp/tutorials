package com.baeldung.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JEP412UnitTest {

    @Test
    void getPrintNameFormat_whenPassingAName_shouldReceiveItFormatted() {
        var jep412 = new JEP412();

        var formattedName = jep412.getPrintNameFormat("John");

        assertEquals("Your name is John", formattedName);
    }
}