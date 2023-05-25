package com.baeldung.truncate;

import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;

public class TruncateString {

    private static final String EMPTY = "";

    public static String usingSubstringMethod(String text, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative");
        }

        if (text == null) {
            return EMPTY;
        }

        if (text.length() <= length) {
            return text;
        } else {
            return text.substring(0, length);
        }
    }

    public static String usingSplitMethod(String text, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative");
        }

        if (text == null) {
            return EMPTY;
        }

        String[] results = text.split("(?<=\\G.{" + length + "})");

        return results[0];
    }

    public static String usingPattern(String text, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative");
        }

        if (text == null) {
            return EMPTY;
        }

        Optional<String> result = Pattern.compile(".{1," + length + "}")
            .matcher(text)
            .results()
            .map(MatchResult::group)
            .findFirst();

        return result.isPresent() ? result.get() : EMPTY;

    }

    public static String usingCodePointsMethod(String text, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative");
        }

        if (text == null) {
            return EMPTY;
        }

        return text.codePoints()
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    public static String usingLeftMethod(String text, int length) {

        return StringUtils.left(text, length);
    }

    public static String usingTruncateMethod(String text, int length) {

        return StringUtils.truncate(text, length);
    }

    public static String usingSplitter(String text, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negative");
        }

        if (text == null) {
            return EMPTY;
        }

        Iterable<String> parts = Splitter.fixedLength(length)
            .split(text);

        return parts.iterator()
            .next();
    }

}
