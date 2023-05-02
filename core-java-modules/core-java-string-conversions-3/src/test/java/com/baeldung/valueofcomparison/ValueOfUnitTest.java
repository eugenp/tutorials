package com.baeldung.valueofcomparison;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ValueOfUnitTest {

    @Test
    void whenCallingValueOf_thenMapToString() {
        char[] exampleCharArray = {'a', 'b', 'c'};
        Student alice = new Student("Alice", 5);

        assertEquals("true", String.valueOf(true));
        assertEquals("a", String.valueOf('a'));
        assertEquals("abc", String.valueOf(exampleCharArray));
        assertEquals("123.935", String.valueOf(123.935));
        assertEquals("2222.3", String.valueOf(2222.3f));
        assertEquals("2222", String.valueOf(2222));
        assertEquals("123456789", String.valueOf(123456789L));
        assertEquals("123456789", String.valueOf(123456789L));
        assertEquals("Student(Alice, age 5)", String.valueOf(alice));
    }
}
