package com.baeldung.objectmutability;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MutableObjectExamplesUnitTest {

    @Test
    public void givenMutableString_whenAppendElement_thenCorrectValue() {
        StringBuilder mutableString = new StringBuilder("Hello");
        mutableString.append(" World");

        assertEquals("Hello World", mutableString.toString());
    }

    @Test
    public void givenMutableList_whenAddElement_thenCorrectSize() {
        List<String> mutableList = new ArrayList<>();
        mutableList.add("Java");

        assertEquals(1, mutableList.size());
    }
}

