package com.baeldung.hamcrest;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringContains.containsStringIgnoringCase;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringEndsWith.endsWithIgnoringCase;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.core.StringStartsWith.startsWithIgnoringCase;
import static org.hamcrest.text.IsBlankString.blankOrNullString;
import static org.hamcrest.text.IsBlankString.blankString;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.junit.Assert.assertThat;

public class HamcrestTextUnitTest {
    
    @Test
    public final void whenTwoStringsAreEqual_thenCorrect() {
        String first = "hello";
        String second = "Hello";
        
        assertThat(first, equalToIgnoringCase(second));
    }

    @Test
    public final void whenTwoStringsAreEqualWithWhiteSpace_thenCorrect() {
        String first = "hello";
        String second = "   Hello   ";
        
        assertThat(first, equalToIgnoringWhiteSpace(second));
    }

    @Test
    public final void whenStringIsBlank_thenCorrect() {
        String first = "  ";
        String second = null;

        assertThat(first, blankString());
        assertThat(first, blankOrNullString());
        assertThat(second, blankOrNullString());
    }

    @Test
    public final void whenStringIsEmpty_thenCorrect() {
        String first = "";
        String second = null;

        assertThat(first, emptyString());
        assertThat(first, emptyOrNullString());
        assertThat(second, emptyOrNullString());
    }

    @Test
    public final void whenStringMatchPattern_thenCorrect() {
        String first = "hello";

        assertThat(first, matchesPattern("[a-z]+"));
    }

    @Test
    public final void whenVerifyStringContains_thenCorrect() {
        String first = "hello";
    
        assertThat(first, containsString("lo"));
        assertThat(first, containsStringIgnoringCase("EL"));
    }
    
    @Test
    public final void whenVerifyStringContainsInOrder_thenCorrect() {
        String first = "hello";
    
        assertThat(first, stringContainsInOrder("e","l","o"));
    }
    
    @Test
    public final void whenVerifyStringStartsWith_thenCorrect() {
        String first = "hello";
    
        assertThat(first, startsWith("he"));
        assertThat(first, startsWithIgnoringCase("HEL"));
    }
    
    @Test
    public final void whenVerifyStringEndsWith_thenCorrect() {
        String first = "hello";
    
        assertThat(first, endsWith("lo"));
        assertThat(first, endsWithIgnoringCase("LO"));
    }
    
}
