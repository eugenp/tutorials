package com.baeldung.isuppercase;

import com.google.common.base.Ascii;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StringFirstCharacterUppercase {

    @Test
    public void givenString_whenCheckingWithCharacterIsUpperCase_thenStringCapitalized() {
        String example = "Katie";
        Assertions.assertTrue(Character.isUpperCase(example.charAt(0)));
    }

    @Test
    public void givenString_whenCheckingWithRegex_thenStringCapitalized() {
        String example = "Katie";
        String regEx = "[A-Z]\\w*";
        assertThat(example, matchesPattern(regEx));
    }

    @Test
    public void givenString_whenCheckingWithGuava_thenStringCapitalized() {
        String example = "Katie";
        Assertions.assertTrue(Ascii.isUpperCase(example.charAt(0)));
    }
}
