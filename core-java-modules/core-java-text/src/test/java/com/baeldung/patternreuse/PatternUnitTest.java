package com.baeldung.patternreuse;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class PatternUnitTest {

    private static final Pattern FIRST_LAST_NAME_PRE_COMPILED_PATTERN = Pattern.compile("[a-zA-Z]{3,} [a-zA-Z]{3,}");
    private static final Pattern SPLIT_PRE_COMPILED_PATTERN = Pattern.compile("__");

    @Test
    public void givenPreCompiledPattern_whenCallMatcher_thenReturnAMatcherToMatches() {
        Matcher matcherName1 = FIRST_LAST_NAME_PRE_COMPILED_PATTERN.matcher("Fabio Silva");
        Matcher matcherName2 = FIRST_LAST_NAME_PRE_COMPILED_PATTERN.matcher("Mr. Silva");

        boolean matchesName1 = matcherName1.matches();
        boolean matchesName2 = matcherName2.matches();

        assertTrue(matchesName1);
        assertFalse(matchesName2);
    }

    @Test
    public void givenPreCompiledPattern_whenCallAsPredicate_thenReturnPredicateToFindThePatternInTheListElements() {
        List<String> namesToValidate = Arrays.asList("Fabio Silva", "Mr. Silva");
        Predicate<String> patternsAsPredicate = FIRST_LAST_NAME_PRE_COMPILED_PATTERN.asPredicate();

        List<String> validNames = namesToValidate.stream()
                .filter(patternsAsPredicate)
                .collect(Collectors.toList());

        assertEquals(1, validNames.size());
        assertTrue(validNames.contains("Fabio Silva"));
    }

    @Test
    public void givenPreCompiledPattern_whenCallSplit_thenReturnArrayWithValuesSplitByThePattern() {
        String[] textSplit = SPLIT_PRE_COMPILED_PATTERN.split("My_Name__is__Fabio_Silva");

        assertEquals("My_Name", textSplit[0]);
        assertEquals("is", textSplit[1]);
        assertEquals("Fabio_Silva", textSplit[2]);
    }

    @Test
    public void givenPreCompiledPattern_whenCallSplitAsStream_thenReturnArrayWithValuesSplitByThePattern() {
        Stream<String> textSplitAsStream = SPLIT_PRE_COMPILED_PATTERN.splitAsStream("My_Name__is__Fabio_Silva");
        String[] textSplit = textSplitAsStream.toArray(String[]::new);

        assertEquals("My_Name", textSplit[0]);
        assertEquals("is", textSplit[1]);
        assertEquals("Fabio_Silva", textSplit[2]);
    }
}
