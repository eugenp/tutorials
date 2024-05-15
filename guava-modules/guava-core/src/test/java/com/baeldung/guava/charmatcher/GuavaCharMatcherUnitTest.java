package com.baeldung.guava.charmatcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Predicate;

public class GuavaCharMatcherUnitTest {

    @Test
    public void whenRemoveSpecialCharacters_thenRemoved() {
        final String input = "H*el.lo,}12";
        final CharMatcher matcher = CharMatcher.javaLetterOrDigit();
        final String result = matcher.retainFrom(input);

        assertEquals("Hello12", result);
    }

    @Test
    public void whenRemoveNonASCIIChars_thenRemoved() {
        final String input = "あhello₤";

        String result = CharMatcher.ascii().retainFrom(input);
        assertEquals("hello", result);

        result = CharMatcher.inRange('0', 'z').retainFrom(input);
        assertEquals("hello", result);
    }

    @Test
    public void whenValidateString_thenValid() {
        final String input = "hello";

        boolean result = CharMatcher.javaLowerCase().matchesAllOf(input);
        assertTrue(result);

        result = CharMatcher.is('e').matchesAnyOf(input);
        assertTrue(result);

        result = CharMatcher.javaDigit().matchesNoneOf(input);
        assertTrue(result);
    }

    @Test
    public void whenTrimString_thenTrimmed() {
        final String input = "---hello,,,";

        String result = CharMatcher.is('-').trimLeadingFrom(input);
        assertEquals("hello,,,", result);

        result = CharMatcher.is(',').trimTrailingFrom(input);
        assertEquals("---hello", result);

        result = CharMatcher.anyOf("-,").trimFrom(input);
        assertEquals("hello", result);
    }

    @Test
    public void whenCollapseFromString_thenCollapsed() {
        final String input = "       hel    lo      ";

        String result = CharMatcher.is(' ').collapseFrom(input, '-');
        assertEquals("-hel-lo-", result);

        result = CharMatcher.is(' ').trimAndCollapseFrom(input, '-');
        assertEquals("hel-lo", result);
    }

    @Test
    public void whenReplaceFromString_thenReplaced() {
        final String input = "apple-banana.";

        String result = CharMatcher.anyOf("-.").replaceFrom(input, '!');
        assertEquals("apple!banana!", result);

        result = CharMatcher.is('-').replaceFrom(input, " and ");
        assertEquals("apple and banana.", result);
    }

    @Test
    public void whenCountCharInString_thenCorrect() {
        final String input = "a, c, z, 1, 2";

        int result = CharMatcher.is(',').countIn(input);
        assertEquals(4, result);

        result = CharMatcher.inRange('a', 'h').countIn(input);
        assertEquals(2, result);
    }

    @Test
    public void whenRemoveCharsNotInCharset_thenRemoved() {
        final Charset charset = Charset.forName("cp437");
        final CharsetEncoder encoder = charset.newEncoder();

        final Predicate<Character> inRange = new Predicate<Character>() {
            @Override
            public boolean apply(final Character c) {
                return encoder.canEncode(c);
            }
        };

        final String result = CharMatcher.forPredicate(inRange).retainFrom("helloは");
        assertEquals("hello", result);
    }

}
