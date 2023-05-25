package com.baeldung.clearstringbuilderorstringbuffer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearStringBuilderOrStringBufferUnitTest {

    @Test
    void whenSetLengthToZero_ThenStringBuilderIsCleared() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello World");
        int initialCapacity = stringBuilder.capacity();
        stringBuilder.setLength(0);
        assertEquals("", stringBuilder.toString());
        assertEquals(initialCapacity, stringBuilder.capacity());
    }

    @Test
    void whenDeleteAll_ThenStringBuilderIsCleared() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello World");
        int initialCapacity = stringBuilder.capacity();
        stringBuilder.delete(0, stringBuilder.length());
        assertEquals("", stringBuilder.toString());
        assertEquals(initialCapacity, stringBuilder.capacity());
    }

    @Test
    void whenSetLengthToZero_ThenStringBufferIsCleared() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Hello World");
        int initialCapacity = stringBuffer.capacity();
        stringBuffer.setLength(0);
        assertEquals("", stringBuffer.toString());
        assertEquals(initialCapacity, stringBuffer.capacity());
    }

    @Test
    void whenDeleteAll_ThenStringBufferIsCleared() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Hello World");
        int initialCapacity = stringBuffer.capacity();
        stringBuffer.delete(0, stringBuffer.length());
        assertEquals("", stringBuffer.toString());
        assertEquals(initialCapacity, stringBuffer.capacity());
    }

    // Note: It did not make the cut to the article, but here is another way to reset a StringBuilder
    @Test
    void whenAssignedToNewStringBuilder_ThenStringBuilderIsCleared() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello World");
        stringBuilder = new StringBuilder();
        assertEquals("", stringBuilder.toString());
    }

    @Test
    void whenAssignedToNewStringBuffer_ThenStringBufferIsCleared() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Hello World");
        stringBuffer = new StringBuffer();
        assertEquals("", stringBuffer.toString());
    }

}
