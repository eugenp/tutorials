package com.baeldung.regex.lookaround;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class LookaroundUnitTest {

    @Test
    void givenPositiveLookahead_thenSuccess() {
        Pattern pattern = Pattern.compile("import (?=static.+).+");

        Matcher matcher = pattern.matcher("import static org.junit.jupiter.api.Assertions.assertEquals;");
        assertTrue(matcher.find());
        assertEquals("import static org.junit.jupiter.api.Assertions.assertEquals;", matcher.group());

        assertFalse(pattern.matcher("import java.util.regex.Matcher;")
            .find());
    }

    @Test
    void givenNegativeLookahead_thenSuccess() {
        Pattern pattern = Pattern.compile("import (?!static.+).+");

        Matcher matcher = pattern.matcher("import java.util.regex.Matcher;");
        assertTrue(matcher.find());
        assertEquals("import java.util.regex.Matcher;", matcher.group());

        assertFalse(pattern.matcher("import static org.junit.jupiter.api.Assertions.assertEquals;")
            .find());
    }

    @Test
    void givenPositiveLookbehind_thenSuccess() {
        Pattern pattern = Pattern.compile(".*(?<=jupiter).*assertEquals;");

        Matcher matcher = pattern.matcher("import static org.junit.jupiter.api.Assertions.assertEquals;");
        assertTrue(matcher.find());
        assertEquals("import static org.junit.jupiter.api.Assertions.assertEquals;", matcher.group());

        assertFalse(pattern.matcher("import static org.junit.Assert.assertEquals;")
            .find());
    }

    @Test
    void givenNegativeLookbehind_thenSuccess() {
        Pattern pattern = Pattern.compile(".*(?<!jupiter.{0,30})assertEquals;");

        Matcher matcher = pattern.matcher("import static org.junit.Assert.assertEquals;");
        assertTrue(matcher.find());
        assertEquals("import static org.junit.Assert.assertEquals;", matcher.group());

        assertFalse(pattern.matcher("import static org.junit.jupiter.api.Assertions.assertEquals;")
            .find());
    }
}
