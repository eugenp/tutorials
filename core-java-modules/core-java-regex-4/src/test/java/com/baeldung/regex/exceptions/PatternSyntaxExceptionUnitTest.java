package com.baeldung.regex.exceptions;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PatternSyntaxExceptionUnitTest {
    // 3.1 Orphaned Quantifier
    @Test
    void givenOrphanedQuantifier_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("*[a-z]"));
    }

    @Test
    void givenValidPatternForOrphanedQuantifierFix_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("[a-z]*abc"); // Fix: quantifier follows valid character class
    }

    // 3.2 Nested Quantifiers Without Grouping
    @Test
    void givenNestedQuantifiersWithoutGrouping_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("\\d+\\.?\\d+*"));
    }

    @Test
    void givenGroupedNestedQuantifiers_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("\\d+(\\.\\d+)*"); // Fix: grouping allows stacking quantifiers
    }

    // 3.3 Unclosed or Malformed Curly Braces
    @Test
    void givenUnclosedCurlyBraces_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("\\d{2,"));
    }

    @Test
    void givenValidCurlyBraces_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("\\d{2,4}"); // Fix: well-formed repetition syntax
    }

    // 3.4 Quantifying Unrepeatable or Improper Elements
    @Test
    void givenImproperQuantifierStacking_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("\\w+\\s+*"));
    }

    @Test
    void givenProperlyGroupedQuantifier_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("(\\w+\\s+)*"); // Fix: quantifier applied to group
    }

    // 3.5 Escaping Literal Quantifier Characters
    @Test
    void givenUnescapedQuantifierCharacters_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("abc+*"));
    }

    @Test
    void givenEscapedQuantifierCharacters_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("abc\\+\\*"); // Fix: escape special characters
    }
}
