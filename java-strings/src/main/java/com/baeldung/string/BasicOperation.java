package com.baeldung.string;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BasicOperation {

    static String toStringWithConcatenation(final char c) {
        return "" + c;
    }

    static String toStringWithValueOf(final char c) {
        return String.valueOf(c);
    }

    static char getChar(final String text, final int index) {
        return text.charAt(index);
    }

    static CharacterIterator getIterator(final String text) {
        return new StringCharacterIterator(text);
    }

    static String append(final String text, final char character) {
        return text + character;
    }

    static String removeWhiteSpace(final String text) {
        return text.replaceAll("\\s+", "");
    }

    static <T> String fromCollection(final Collection<T> collection) {
        return collection.stream().map(Objects::toString).collect(Collectors.joining(", ", "[", "]"));
    }

    static String[] splitByRegExPipe(final String text) {
        return text.split("\\Q|\\E");
    }

    static String[] splitByPatternPipe(final String text) {
        return text.split(Pattern.quote("|"));
    }

    static int asciiValue(final char character) {
        return (int) character;
    }

    static char fromAsciiValue(final int value){
        return (char) value;
    }

    private BasicOperation() {
        //
    }
}
