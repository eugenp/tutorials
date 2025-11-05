package com.baeldung.insertnewlineinstringbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringBuilderHelperTest {

    @Test
    public void whenAppendingString_thenCorrectStringIsBuilt() {
        StringBuilderHelper gsBuilder = new StringBuilderHelper();
        gsBuilder.append("Hello")
                .appendLineSeparator()
                .append("World");

        assertEquals("Hello" + System.lineSeparator() + "World", gsBuilder.toString());
    }

    @Test
    public void whenAppendingInteger_thenCorrectStringIsBuilt() {
        StringBuilderHelper gsBuilder = new StringBuilderHelper();
        gsBuilder.append(123)
                .appendLineSeparator()
                .append(456);

        assertEquals("123" + System.lineSeparator() + "456", gsBuilder.toString());
    }

}