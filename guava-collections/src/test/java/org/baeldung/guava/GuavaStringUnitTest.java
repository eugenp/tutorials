package org.baeldung.guava;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.*;

import com.google.common.collect.*;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;

public class GuavaStringUnitTest {

    @Test
    public void whenConvertListToString_thenConverted() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").join(names);

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenConvertListToStringAndSkipNull_thenConverted() {
        final List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").skipNulls().join(names);

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenConvertMapToString_thenConverted() {
        final Map<String, Integer> salary = Maps.newHashMap();
        salary.put("John", 1000);
        salary.put("Jane", 1500);

        final String result = Joiner.on(" , ").withKeyValueSeparator(" = ").join(salary);

        assertThat(result, containsString("John = 1000"));
        assertThat(result, containsString("Jane = 1500"));
    }

    @Test
    public void whenJoinNestedCollections_thenJoined() {
        final List<ArrayList<String>> nested = Lists.newArrayList(Lists.newArrayList("apple", "banana", "orange"), Lists.newArrayList("cat", "dog", "bird"), Lists.newArrayList("John", "Jane", "Adam"));
        final String result = Joiner.on(";").join(Iterables.transform(nested, new Function<List<String>, String>() {
            @Override
            public final String apply(final List<String> input) {
                return Joiner.on("-").join(input);
            }
        }));

        assertThat(result, containsString("apple-banana-orange"));
        assertThat(result, containsString("cat-dog-bird"));
        assertThat(result, containsString("John-Jane-Adam"));
    }

    @Test
    public void whenUseForNull_thenUsed() {
        final List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").useForNull("nameless").join(names);

        assertEquals(result, "John,nameless,Jane,Adam,Tom");
    }

    @Test
    public void whenCreateListFromString_thenCreated() {
        final String input = "apple - banana - orange";
        final List<String> result = Splitter.on("-").trimResults().splitToList(input);

        assertThat(result, contains("apple", "banana", "orange"));
    }

    @Test
    public void whenCreateMapFromString_thenCreated() {
        final String input = "John=first,Adam=second";
        final Map<String, String> result = Splitter.on(",").withKeyValueSeparator("=").split(input);

        assertEquals("first", result.get("John"));
        assertEquals("second", result.get("Adam"));
    }

    @Test
    public void whenSplitStringOnMultipleSeparator_thenSplit() {
        final String input = "apple.banana,,orange,,.";
        final List<String> result = Splitter.onPattern("[.,]").omitEmptyStrings().splitToList(input);

        assertThat(result, contains("apple", "banana", "orange"));
    }

    @Test
    public void whenSplitStringOnSpecificLength_thenSplit() {
        final String input = "Hello world";
        final List<String> result = Splitter.fixedLength(3).splitToList(input);

        assertThat(result, contains("Hel", "lo ", "wor", "ld"));
    }

    @Test
    public void whenLimitSplitting_thenLimited() {
        final String input = "a,b,c,d,e";
        final List<String> result = Splitter.on(",").limit(4).splitToList(input);

        assertEquals(4, result.size());
        assertThat(result, contains("a", "b", "c", "d,e"));
    }

    @Test
    public void whenRemoveSpecialCharacters_thenRemoved() {
        final String input = "H*el.lo,}12";
        final CharMatcher matcher = CharMatcher.JAVA_LETTER_OR_DIGIT;
        final String result = matcher.retainFrom(input);

        assertEquals("Hello12", result);
    }

    @Test
    public void whenRemoveNonASCIIChars_thenRemoved() {
        final String input = "あhello₤";

        String result = CharMatcher.ASCII.retainFrom(input);
        assertEquals("hello", result);

        result = CharMatcher.inRange('0', 'z').retainFrom(input);
        assertEquals("hello", result);
    }

    @Test
    public void whenValidateString_thenValid() {
        final String input = "hello";

        boolean result = CharMatcher.JAVA_LOWER_CASE.matchesAllOf(input);
        assertTrue(result);

        result = CharMatcher.is('e').matchesAnyOf(input);
        assertTrue(result);

        result = CharMatcher.JAVA_DIGIT.matchesNoneOf(input);
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
