package com.baeldung.regex.countmatches;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test intended to count number of matches of a RegEx using Java 8 and 9.
 * 
 * Java 9 is needed to run the test.
 */
public class CountMatchesUnitTest {

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
    private static final String TEXT_CONTAINING_EMAIL_ADDRESSES = "You can contact me through: writer@baeldung.com, editor@baeldung.com and team@bealdung.com";

    private Matcher countEmailMatcher;

    @Before
    public void setup() {
        countEmailMatcher = EMAIL_ADDRESS_PATTERN.matcher(TEXT_CONTAINING_EMAIL_ADDRESSES);
    }

    @Test
    public void givenContainingEmailString_whenJava8Match_thenCountMacthesFound() {
        int count = 0;
        while (countEmailMatcher.find()) {
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    public void givenContainingEmailString_whenUsingSplit_thenCountMacthesFound() {
        long count = EMAIL_ADDRESS_PATTERN.split(TEXT_CONTAINING_EMAIL_ADDRESSES).length;

        assertEquals(3, count);
    }

    @Test
    public void givenContainingEmailString_whenUsingSplitAsStream_thenCountMacthesFound() {
        long count = EMAIL_ADDRESS_PATTERN.splitAsStream(TEXT_CONTAINING_EMAIL_ADDRESSES)
            .count();

        assertEquals(3, count);
    }

    @Test
    public void givenContainingEmailString_whenStartingInJava9Match_thenCountMacthesFound() {
        long count = countEmailMatcher.results()
            .count();

        assertEquals(3, count);
    }
}
