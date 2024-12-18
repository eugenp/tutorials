package com.baeldung.cats.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    private static final Pattern ZERO_WIDTH_PATTERN = Pattern.compile("[\u200B\u200C\u200D\u200F\u202B\u200E\uFEFF]");

    private RegexUtils() {
    }

    public static String removeZeroWidthChars(String value) {
        return value == null ? null
            : ZERO_WIDTH_PATTERN.matcher(value)
                .replaceAll("");
    }
}
