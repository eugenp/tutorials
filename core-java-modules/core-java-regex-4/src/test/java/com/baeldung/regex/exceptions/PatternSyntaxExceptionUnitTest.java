package com.baeldung.regex.exceptions;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PatternSyntaxExceptionUnitTest {
    // 3.1 Orphaned Quantifier
    @Test
    void givenOrphanedQuantifier_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("*abc"));
    }

    @Test
    void givenValidPatternForOrphanedQuantifierFix_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("a*bc"); // Fix: quantifier follows valid element
    }

    // 3.2 Nested Quantifiers Without Grouping
    @Test
    void givenNestedQuantifiersWithoutGrouping_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("a**"));
    }

    @Test
    void givenGroupedNestedQuantifiers_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("(a*)*"); // Fix: grouping allows stacking quantifiers
    }

    // 3.3 Unclosed or Malformed Curly Braces
    @Test
    void givenUnclosedCurlyBraces_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("a{2,3"));
    }

    @Test
    void givenValidCurlyBraces_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("a{2,3}"); // Fix: well-formed repetition syntax
    }

    // 3.4 Quantifying Unrepeatable or Improper Elements
    @Test
    void givenImproperQuantifierStacking_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("a+*"));
    }

    @Test
    void givenProperlyGroupedQuantifier_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("(a+)*"); // Fix: quantifier applied to group
    }

    // 3.5 Escaping Literal Quantifier Characters
    @Test
    void givenUnescapedQuantifierCharacters_whenCompiled_thenThrowsPatternSyntaxException() {
        assertThrows(PatternSyntaxException.class, () -> Pattern.compile("a+*"));
    }

    @Test
    void givenEscapedQuantifierCharacters_whenCompiled_thenCompilesSuccessfully() {
        Pattern.compile("a\\+\\*"); // Fix: escape special characters
    }
}