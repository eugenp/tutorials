package com.baeldung.chararraytostring;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CharArrayToStringConversionUnitTest {

    @Test
    public void whenStringConstructor_thenOK() {
        final char[] charArray = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };

        String string = new String(charArray);

        assertThat(string, is("baeldung"));
    }

    @Test
    public void whenStringCopyValueOf_thenOK() {
        final char[] charArray = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };

        String string = String.copyValueOf(charArray);

        assertThat(string, is("baeldung"));
    }

    @Test
    public void whenStringValueOf_thenOK() {
        final char[] charArray = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };

        String string = String.valueOf(charArray);

        assertThat(string, is("baeldung"));
    }

    @Test
    public void whenStringBuilder_thenOK() {
        final char[][] arrayOfCharArray = { { 'b', 'a' }, { 'e', 'l', 'd', 'u' }, { 'n', 'g' } };

        StringBuilder sb = new StringBuilder();
        for (char[] subArray : arrayOfCharArray) {
            sb.append(subArray);
        }

        assertThat(sb.toString(), is("baeldung"));
    }

    @Test
    public void whenStreamCollectors_thenOK() {
        final Character[] charArray = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };

        Stream<Character> charStream = Arrays.stream(charArray);
        String string = charStream.map(String::valueOf).collect(Collectors.joining());

        assertThat(string, is("baeldung"));
    }

    @Test
    public void whenGoogleCommonBaseJoiners_thenOK() {
        final Character[] charArray = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };

        String string = Joiner.on("|").join(charArray);

        assertThat(string, is("b|a|e|l|d|u|n|g"));
    }
}
