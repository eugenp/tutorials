package com.baeldung.titlecase;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TitleCaseConverter {

    private static final String WORD_SEPARATOR = " ";

    public static String convertToTitleCaseIteratingChars(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    public static String convertToTitleCaseSplitting(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays
          .stream(text.split(WORD_SEPARATOR))
          .map(word -> word.isEmpty()
            ? word
            : Character.toTitleCase(word.charAt(0)) + word
              .substring(1)
              .toLowerCase())
          .collect(Collectors.joining(WORD_SEPARATOR));
    }

    public static String convertToTitleCaseIcu4j(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return UCharacter.toTitleCase(text, BreakIterator.getTitleInstance());
    }

    public static String convertToTileCaseWordUtilsFull(String text) {
        return WordUtils.capitalizeFully(text);
    }

    public static String convertToTileCaseWordUtils(String text) {
        return WordUtils.capitalize(text);
    }

}
