package com.baeldung.passstringbyreference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class PassStringUnitTest {

    @Test
    void givenAString_whenPassedToVoidMethod_thenStringIsNotModified() {
        String s = "hello";
        concatStringWithNoReturn(s);
        assertEquals("hello", s);
    }

    void concatStringWithNoReturn(String input) {
        input += " world";
        assertEquals("hello world", input);
    }

    @Test
    void givenAString_whenPassedToMethodAndReturnNewString_thenStringIsModified() {
        String s = "hello";
        assertEquals("hello world", concatStringWithReturn(s));
    }

    String concatStringWithReturn(String input) {
        return input + " world";
    }

    @Test
    void givenAString_whenPassStringBuilderToVoidMethod_thenConcatNewStringOk() {
        StringBuilder builder = new StringBuilder("hello");
        concatWithStringBuilder(builder);

        assertEquals("hello world", builder.toString());
    }

    void concatWithStringBuilder(StringBuilder input) {
        input.append(" world");
    }

    @Test
    void givenAString_whenPassStringBufferToVoidMethod_thenConcatNewStringOk() {
        StringBuffer builder = new StringBuffer("hello");
        concatWithStringBuffer(builder);

        assertEquals("hello world", builder.toString());
    }

    void concatWithStringBuffer(StringBuffer input) {
        input.append(" world");
    }

    @Test
    void givenObjectWithStringField_whenSetDifferentValue_thenObjectIsModified() {
        Dummy dummy = new Dummy();
        assertNull(dummy.getDummyString());
        modifyStringValueInInputObject(dummy, "hello world");
        assertEquals("hello world", dummy.getDummyString());
    }

    void modifyStringValueInInputObject(Dummy dummy, String dummyString) {
        dummy.setDummyString(dummyString);
    }

    @Test
    void givenObjectWithStringField_whenSetDifferentValueWithStringBuilder_thenSetStringInNewObject() {
        assertEquals("hello world", getDummy("hello", "world").getDummyString());
    }

    Dummy getDummy(String hello, String world) {
        StringBuilder builder = new StringBuilder();

        builder.append(hello)
          .append(" ")
          .append(world);

        Dummy dummy = new Dummy();
        dummy.setDummyString(builder.toString());

        return dummy;
    }
}
