package com.baeldung.pattern;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatternJava11UnitTest {

    private static final String VALID_NAME = "Fabio Silva";
    private static final String INVALID_NAME = "Fabio Luis Silva";
    private static final List<String> NAMES_TO_VALIDATE = Arrays.asList(VALID_NAME, INVALID_NAME);
    private static final Pattern FIRST_LAST_NAME_PRE_COMPILED_PATTERN = Pattern.compile("[a-zA-Z]{3,} [a-zA-Z]{3,}");

    @Test
    public void givenPreCompiledPattern_whenCallAsMatchPredicate_thenReturnMatchPredicateToMatchesThePatternInTheListElements() {
        Predicate<String> patternAsMatchPredicate = FIRST_LAST_NAME_PRE_COMPILED_PATTERN.asMatchPredicate();
        List<String> validatedNames = NAMES_TO_VALIDATE.stream()
                .filter(patternAsMatchPredicate)
                .collect(Collectors.toList());

        assertTrue(validatedNames.contains(VALID_NAME));
        assertFalse(validatedNames.contains(INVALID_NAME));
    }
}
