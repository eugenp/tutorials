package com.baeldung.regex.countmatches;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Test intended to count number of matches of a RegEx using Java 8 and 9.
 * 
 * Java 9 is needed to run the commented out tests.
 */
public class CountMatchesUnitTest {

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
    private static final String TEXT_CONTAINING_EMAIL_ADDRESSES = "You can contact me through: writer@baeldung.com, editor@baeldung.com and team@bealdung.com";
    private static final String TEXT_CONTAINING_FIVE_EMAIL_ADDRESSES = "Valid emails are: me@gmail.com, you@baeldung.com, contact@hotmail.com, press@anysite.com and support@bealdung.com";
    private static final String TEXT_CONTAINING_OVERLAP_EMAIL_ADDRESSES = "Try to contact us at team@baeldung.comeditor@baeldung.com, support@baeldung.com.";

    @Test
    public void givenContainingEmailString_whenJava8Match_thenCountMacthesFound() {
        Matcher countEmailMatcher = EMAIL_ADDRESS_PATTERN.matcher(TEXT_CONTAINING_EMAIL_ADDRESSES);

        int count = 0;
        while (countEmailMatcher.find()) {
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    public void givenContainingFiveEmailsString_whenJava8Match_thenCountMacthesFound() {
        Matcher countFiveEmailsMatcher = EMAIL_ADDRESS_PATTERN.matcher(TEXT_CONTAINING_FIVE_EMAIL_ADDRESSES);

        int count = 0;
        while (countFiveEmailsMatcher.find()) {
            count++;
        }

        assertEquals(5, count);
    }

    @Test
    public void givenStringWithoutEmails_whenJava8Match_thenCountMacthesNotFound() {
        Matcher noEmailMatcher = EMAIL_ADDRESS_PATTERN.matcher("Simple text without emails.");

        int count = 0;
        while (noEmailMatcher.find()) {
            count++;
        }

        assertEquals(0, count);
    }

    @Test
    public void givenStringWithOverlappingEmails_whenJava8Match_thenCountWrongMatches() {
        Matcher countOverlappingEmailsMatcher = EMAIL_ADDRESS_PATTERN.matcher(TEXT_CONTAINING_OVERLAP_EMAIL_ADDRESSES);

        int count = 0;
        while (countOverlappingEmailsMatcher.find()) {
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    public void givenContainingEmailString_whenStartingInJava9Match_thenCountMacthesFound() {
        // uncomment to try with Java 9
        // Matcher countEmailMatcher = EMAIL_ADDRESS_PATTERN.matcher(TEXT_CONTAINING_EMAIL_ADDRESSES);

        // long count = countEmailMatcher.results().count();

        // assertEquals(3, count);
    }

    @Test
    public void givenContainingFiveEmailsString_whenStartingInJava9Match_thenCountMacthesFound() {
        // uncomment to try with Java 9
        // Matcher countFiveEmailsMatcher = EMAIL_ADDRESS_PATTERN.matcher(TEXT_CONTAINING_FIVE_EMAIL_ADDRESSES);

        // long count = countFiveEmailsMatcher.results().count();

        // assertEquals(5, count);
    }

    @Test
    public void givenStringWithoutEmails_whenJava9Match_thenCountMacthesNotFound() {
        // uncomment to try with Java 9
        // Matcher noEmailMatcher = EMAIL_ADDRESS_PATTERN.matcher("Simple text without emails.");

        // long count = noEmailMatcher.results().count();

        // assertEquals(0, count);
    }

    @Test
    public void givenStringWithOverlappingEmails_whenJava9Match_thenCountWrongMatches() {
        // uncomment to try with Java 9
        // Matcher countOverlappingEmailsMatcher = EMAIL_ADDRESS_PATTERN.matcher(TEXT_CONTAINING_OVERLAP_EMAIL_ADDRESSES);

        // long count = countOverlappingEmailsMatcher.results().count();

        // assertNotEquals(3, count);
    }
}
