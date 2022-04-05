package com.baeldung.replacetokens;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacingTokens {
    public static final Pattern TITLE_CASE_PATTERN = Pattern.compile("(?<=^|[^A-Za-z])([A-Z][a-z]*)(?=[^A-Za-z]|$)");

    /**
     * Iterate over the title case tokens in the input and replace them with lowercase
     * @param original the original string
     * @return a string with words replaced with their lowercase equivalents
     */
    public static String replaceTitleCaseWithLowerCase(String original) {
        int lastIndex = 0;
        StringBuilder output = new StringBuilder();
        Matcher matcher = TITLE_CASE_PATTERN.matcher(original);
        while (matcher.find()) {
            output.append(original, lastIndex, matcher.start())
                    .append(convert(matcher.group(1)));

            lastIndex = matcher.end();
        }
        if (lastIndex < original.length()) {
            output.append(original, lastIndex, original.length());
        }
        return output.toString();
    }

    /**
     * Convert a token found into its desired lowercase
     * @param token the token to convert
     * @return the converted token
     */
    private static String convert(String token) {
        return token.toLowerCase();
    }

    /**
     * Replace all the tokens in an input using the algorithm provided for each
     * @param original original string
     * @param tokenPattern the pattern to match with
     * @param converter the conversion to apply
     * @return the substituted string
     */
    public static String replaceTokens(String original, Pattern tokenPattern,
                                       Function<Matcher, String> converter) {
        int lastIndex = 0;
        StringBuilder output = new StringBuilder();
        Matcher matcher = tokenPattern.matcher(original);
        while (matcher.find()) {
            output.append(original, lastIndex, matcher.start())
                    .append(converter.apply(matcher));

            lastIndex = matcher.end();
        }
        if (lastIndex < original.length()) {
            output.append(original, lastIndex, original.length());
        }
        return output.toString();
    }
}
