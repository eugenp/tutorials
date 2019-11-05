package com.baeldung.stringcomparison;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringComparisonUnitTest {

    @Test
    public void whenUsingComparisonOperator_ThenComparingStrings() {

        String string1 = "using comparison operator";
        String string2 = "using comparison operator";
        String string3 = new String("using comparison operator");

        assertThat(string1 == string2).isTrue();
        assertThat(string1 == string3).isFalse();
    }

    @Test
    public void whenUsingEqualsMethod_ThenComparingStrings() {

        String string1 = "using equals method";
        String string2 = "using equals method";

        String string3 = "using EQUALS method";
        String string4 = new String("using equals method");

        assertThat(string1.equals(string2)).isTrue();
        assertThat(string1.equals(string4)).isTrue();

        assertThat(string1.equals(null)).isFalse();
        assertThat(string1.equals(string3)).isFalse();
    }

    @Test
    public void whenUsingEqualsIgnoreCase_ThenComparingStrings() {

        String string1 = "using equals ignore case";
        String string2 = "USING EQUALS IGNORE CASE";

        assertThat(string1.equalsIgnoreCase(string2)).isTrue();
    }

    @Test
    public void whenUsingCompareTo_ThenComparingStrings() {

        String author = "author";
        String book = "book";
        String duplicateBook = "book";

        assertThat(author.compareTo(book)).isEqualTo(-1);
        assertThat(book.compareTo(author)).isEqualTo(1);
        assertThat(duplicateBook.compareTo(book)).isEqualTo(0);
    }

    @Test
    public void whenUsingCompareToIgnoreCase_ThenComparingStrings() {

        String author = "Author";
        String book = "book";
        String duplicateBook = "BOOK";

        assertThat(author.compareToIgnoreCase(book)).isEqualTo(-1);
        assertThat(book.compareToIgnoreCase(author)).isEqualTo(1);
        assertThat(duplicateBook.compareToIgnoreCase(book)).isEqualTo(0);
    }

    @Test
    public void whenUsingObjectsEqualsMethod_ThenComparingStrings() {

        String string1 = "using objects equals";
        String string2 = "using objects equals";
        String string3 = new String("using objects equals");

        assertThat(Objects.equals(string1, string2)).isTrue();
        assertThat(Objects.equals(string1, string3)).isTrue();

        assertThat(Objects.equals(null, null)).isTrue();
        assertThat(Objects.equals(null, string1)).isFalse();
    }

    @Test
    public void whenUsingEqualsOfApacheCommons_ThenComparingStrings() {

        assertThat(StringUtils.equals(null, null)).isTrue();
        assertThat(StringUtils.equals(null, "equals method")).isFalse();
        assertThat(StringUtils.equals("equals method", "equals method")).isTrue();
        assertThat(StringUtils.equals("equals method", "EQUALS METHOD")).isFalse();
    }

    @Test
    public void whenUsingEqualsIgnoreCaseOfApacheCommons_ThenComparingStrings() {

        assertThat(StringUtils.equalsIgnoreCase(null, null)).isTrue();
        assertThat(StringUtils.equalsIgnoreCase(null, "equals method")).isFalse();
        assertThat(StringUtils.equalsIgnoreCase("equals method", "equals method")).isTrue();
        assertThat(StringUtils.equalsIgnoreCase("equals method", "EQUALS METHOD")).isTrue();
    }

    @Test
    public void whenUsingEqualsAnyOf_ThenComparingStrings() {

        assertThat(StringUtils.equalsAny(null, null, null)).isTrue();
        assertThat(StringUtils.equalsAny("equals any", "equals any", "any")).isTrue();
        assertThat(StringUtils.equalsAny("equals any", null, "equals any")).isTrue();
        assertThat(StringUtils.equalsAny(null, "equals", "any")).isFalse();
        assertThat(StringUtils.equalsAny("equals any", "EQUALS ANY", "ANY")).isFalse();
    }

    @Test
    public void whenUsingEqualsAnyIgnoreCase_ThenComparingStrings() {

        assertThat(StringUtils.equalsAnyIgnoreCase(null, null, null)).isTrue();
        assertThat(StringUtils.equalsAnyIgnoreCase("equals any", "equals any", "any")).isTrue();
        assertThat(StringUtils.equalsAnyIgnoreCase("equals any", null, "equals any")).isTrue();
        assertThat(StringUtils.equalsAnyIgnoreCase(null, "equals", "any")).isFalse();
        assertThat(StringUtils.equalsAnyIgnoreCase("equals any ignore case", "EQUALS ANY IGNORE CASE", "any")).isTrue();
    }

    @Test
    public void whenUsingCompare_thenComparingStringsWithNulls() {

        assertThat(StringUtils.compare(null, null)).isEqualTo(0);
        assertThat(StringUtils.compare(null, "abc")).isEqualTo(-1);

        assertThat(StringUtils.compare("abc", "bbc")).isEqualTo(-1);
        assertThat(StringUtils.compare("bbc", "abc")).isEqualTo(1);
        assertThat(StringUtils.compare("abc", "abc")).isEqualTo(0);
    }

    @Test
    public void whenUsingCompareIgnoreCase_ThenComparingStringsWithNulls() {

        assertThat(StringUtils.compareIgnoreCase(null, null)).isEqualTo(0);
        assertThat(StringUtils.compareIgnoreCase(null, "abc")).isEqualTo(-1);

        assertThat(StringUtils.compareIgnoreCase("Abc", "bbc")).isEqualTo(-1);
        assertThat(StringUtils.compareIgnoreCase("bbc", "ABC")).isEqualTo(1);
        assertThat(StringUtils.compareIgnoreCase("abc", "ABC")).isEqualTo(0);
    }

    @Test
    public void whenUsingCompareWithNullIsLessOption_ThenComparingStrings() {

        assertThat(StringUtils.compare(null, "abc", true)).isEqualTo(-1);
        assertThat(StringUtils.compare(null, "abc", false)).isEqualTo(1);
    }

}
