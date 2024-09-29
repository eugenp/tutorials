package com.baeldung.replaceall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.Test;

public class StringReplaceAllUnitTest {

    @Test
    void whenUseReplaceAll_thenCorrect() {
        String input = "Hello world";
        String result = input.replaceAll("\\s", "_");
        assertEquals("Hello_world", result);

        //greedy
        result = input.replaceAll("e.*o", "X");
        assertEquals("HXrld", result);

        //non-greedy
        result = input.replaceAll("e.*?o", "X");
        assertEquals("HX world", result);

        result = input.replaceAll("e[^o]*o", "X");
        assertEquals("HX world", result);
    }

    @Test
    void whenUseReplace_thenPerformLiteralStrReplacement() {
        String input = "hello.java.hello.world";
        String replaceResult = input.replace("hello", "hi");
        assertEquals("hi.java.hi.world", replaceResult);

        String replaceAllResult = input.replaceAll("hello", "hi");
        assertEquals("hi.java.hi.world", replaceAllResult);

        replaceResult = input.replace(".", ":");
        assertEquals("hello:java:hello:world", replaceResult);

        replaceAllResult = input.replaceAll(".", ":");
        assertEquals("::::::::::::::::::::::", replaceAllResult);
    }

    @Test
    void whenUseReplaceAllWithSpecialChars_thenCorrect() {
        String input = "hi.java.hi.world";

        String result = input.replaceAll("\\.", ":");
        assertEquals("hi:java:hi:world", result);

        result = input.replaceAll("[.]", ":");
        assertEquals("hi:java:hi:world", result);

        input = "(debug) hello.world";
        result = input.replaceAll("(debug)", "[info]");
        assertEquals("([info]) hello.world", result);

        result = input.replaceAll("[(]debug[)]", "[info]");
        assertEquals("[info] hello.world", result);

        result = input.replaceAll("\\(debug\\)", "[info]");
        assertEquals("[info] hello.world", result);
    }

    @Test
    void whenUseReplaceAllWithInvalidRegex_thenRaiseException() {
        String input = "Hello world";
        assertThrows(PatternSyntaxException.class, () -> input.replaceAll("e**", "X"));
    }

    @Test
    void whenUsingBackReferenceInReplaceAll_thenCorrect() {
        String input = "123456789";
        String expected = "789-456-123";

        String result = input.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$3-$2-$1");
        assertEquals(expected, result);
    }


    @Test
    void whenUsingLookaroundWithReplaceAll_thenCorrect() {
        String input = "abcdefg";
        String expected = "a-b-c-d-e-f-g";

        String result = input.replaceAll("(?<=.)(?=.)", "-");
        assertEquals(expected, result);
    }
}