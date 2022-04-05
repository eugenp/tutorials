package com.baeldung.regex.camelcasetowords;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert a string in camelCase or TitleCase into a list of words
 */
public class CamelCaseToWords {
    private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");

    /**
     * Find the words in mixed case string like ThisIsText or HereIsSomeText
     * @param text the text to parse
     * @return the list of words to process
     */
    public static List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }
}
