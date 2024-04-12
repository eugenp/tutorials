package com.baeldung.split;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SplitStringEveryNthCharUnitTest {

    public static final String TEXT = "abcdefgh123456";

    @Test
    public void givenString_whenUsingSplit_thenSplit() {
        List<String> results = SplitStringEveryNthChar.usingSplitMethod(TEXT, 3);

        assertThat(results, contains("abc", "def", "gh1", "234", "56"));
    }

    @Test
    public void givenString_whenUsingSubstring_thenSplit() {
        List<String> results = SplitStringEveryNthChar.usingSubstringMethod(TEXT, 4);

        assertThat(results, contains("abcd", "efgh", "1234", "56"));
    }

    @Test
    public void givenString_whenUsingPattern_thenSplit() {
        List<String> results = SplitStringEveryNthChar.usingPattern(TEXT, 5);

        assertThat(results, contains("abcde", "fgh12", "3456"));
    }

    @Test
    public void givenString_whenUsingGuava_thenSplit() {
        List<String> results = SplitStringEveryNthChar.usingGuava(TEXT, 6);

        assertThat(results, contains("abcdef", "gh1234", "56"));
    }

}
