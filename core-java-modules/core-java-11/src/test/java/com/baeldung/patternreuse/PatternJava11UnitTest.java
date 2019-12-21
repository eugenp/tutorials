package com.baeldung.patternreuse;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatternJava11UnitTest {

    @Test
    public void givenPreCompiledPattern_whenCallAsMatchPredicate_thenReturnMatchPredicateToMatchesThePatternInTheListElements() {
        List<String> namesToValidate = Arrays.asList("Fabio Silva", "Fabio Luis Silva");
        Pattern firstLastNamePreCompiledPattern = Pattern.compile("[a-zA-Z]{3,} [a-zA-Z]{3,}");

        Predicate<String> patternAsMatchPredicate = firstLastNamePreCompiledPattern.asMatchPredicate();
        List<String> validatedNames = namesToValidate.stream()
                .filter(patternAsMatchPredicate)
                .collect(Collectors.toList());

        assertTrue(validatedNames.contains("Fabio Silva"));
        assertFalse(validatedNames.contains("Fabio Luis Silva"));
    }
}
