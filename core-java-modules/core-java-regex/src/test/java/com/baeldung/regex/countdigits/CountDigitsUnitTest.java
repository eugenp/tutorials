package com.baeldung.regex.countdigits;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.google.common.base.CharMatcher;

/**
 * Unit Test to count the number of digits in a String
 */
class CountDigitsUnitTest {

    // Guava CharMatcher to match digits
    private static final CharMatcher DIGIT_CHAR_MATCHER = CharMatcher.inRange('0', '9');
    
    private static final String STR_WITH_ALL_DIGITS = "970987678607608";
    private static final String STR_WITH_SINGLE_DIGITS_SEP_BY_NON_DIGITS = "9kjl()4f*(&6~3dfd8&5dfd8a";
    private static final String STR_WITH_SEQUENCES_OF_1_OR_MORE_DIGITS_SEP_BY_NON_DIGITS
      = "64.6lk.l~453lkdsf9wg038.68*()(k;95786fsd7986";
    
    private static int countDigits(String stringToSearch) {
        Matcher countEmailMatcher = Pattern.compile("\\d").matcher(stringToSearch);

        int count = 0;
        while (countEmailMatcher.find()) {
            count++;
        }

        return count;
    }

    @Test
    void givenStrOfAllDigits_whenRegexMatchByDigit_thenFifteenDigitsCounted() {
        int count = countDigits(STR_WITH_ALL_DIGITS);

        assertThat(count).isEqualTo(15);
    }

    @Test
    void givenStrWithSingleDigitsSepByNonDigits_whenRegexMatchByDigit_thenSevenDigitsCounted() {
        int count = countDigits(STR_WITH_SINGLE_DIGITS_SEP_BY_NON_DIGITS);

        assertThat(count).isEqualTo(7);
    }

    @Test
    void givenStrWithOneOrMoreDigitsSepByNonDigits_whenRegexMatchByDigit_thenTwentyOneDigitsCounted() {
        int count = countDigits(STR_WITH_SEQUENCES_OF_1_OR_MORE_DIGITS_SEP_BY_NON_DIGITS);

        assertThat(count).isEqualTo(21);
    }
    
    @Test
    void givenStrOfAllDigits_whenGuavaCharMatchByDigit_thenFifteenDigitsCounted() {
        int count = DIGIT_CHAR_MATCHER.countIn(STR_WITH_ALL_DIGITS);

        assertThat(count).isEqualTo(15);
    }
    
    @Test
    void givenStrWithSingleDigitsSepByNonDigits_whenGuavaCharMatchByDigit_thenSevenDigitsCounted() {
        int count = DIGIT_CHAR_MATCHER.countIn(STR_WITH_SINGLE_DIGITS_SEP_BY_NON_DIGITS);

        assertThat(count).isEqualTo(7);
    }
    
    @Test
    void givenStrWithOneOrMoreDigitsSepByNonDigits_whenGuavaCharMatchByDigit_thenTwentyOneDigitsCounted() {
        int count = DIGIT_CHAR_MATCHER.countIn(STR_WITH_SEQUENCES_OF_1_OR_MORE_DIGITS_SEP_BY_NON_DIGITS);

        assertThat(count).isEqualTo(21);
    }
}
