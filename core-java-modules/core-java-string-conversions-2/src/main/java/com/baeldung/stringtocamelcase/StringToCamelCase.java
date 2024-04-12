package com.baeldung.stringtocamelcase;

import com.google.common.base.CaseFormat;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import org.apache.commons.text.CaseUtils;
import org.apache.commons.text.WordUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringToCamelCase {

    public static String toCamelCaseByIteration(String text, char delimiter) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        boolean shouldConvertNextCharToLower = true;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar == delimiter) {
                shouldConvertNextCharToLower = false;
            } else if (shouldConvertNextCharToLower) {
                builder.append(Character.toLowerCase(currentChar));
            } else {
                builder.append(Character.toUpperCase(currentChar));
                shouldConvertNextCharToLower = true;
            }
        }
        return builder.toString();
    }

    public static String toCamelCaseBySplitting(String text, String delimiter) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String[] words = text.split(delimiter);
        StringBuilder builder = new StringBuilder();
        for (int i = 0, wordsLength = words.length; i < wordsLength; i++) {
            String word = words[i];
            if (i == 0) {
                //Make the first word all lowercase
                word = word.isEmpty() ? word : word.toLowerCase();
            } else {
                //Convert the first character to Uppercase and others to lowercase
                // e.g sTRING =====> String
                word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            builder.append(word);
        }
        return builder.toString();
    }

    public static String toCamelCaseBySplittingUsingStreams(String text, String delimiter) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String[] words = text.split(delimiter);
        //Convert the first word to lowercase and then every
        //other word to Title Case.
        String firstWord = words[0].toLowerCase();
        String otherWords = Arrays.stream(words, 1, words.length)
          .filter(word -> !word.isEmpty())
          .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
          .collect(Collectors.joining(""));

        return firstWord + otherWords;
    }

    public static String toCamelCaseByRegex(String text) {
        StringBuilder builder = new StringBuilder();
        String[] words = text.split("[\\W_]+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i == 0) {
                word = word.isEmpty() ? word : word.toLowerCase();
            } else {
                word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            builder.append(word);
        }
        return builder.toString();
    }

    //Third-Party Libraries
    public static String toCamelCaseUsingICU4J(String text, String delimiter) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        text = UCharacter.toTitleCase(text, BreakIterator.getTitleInstance()).replaceAll(delimiter, "");
        StringBuilder builder = new StringBuilder(text);
        builder.setCharAt(0, Character.toLowerCase(text.charAt(0)));
        return builder.toString();
    }

    public static String toCamelCaseUsingGuava(String text, String delimiter) {
        String toUpperUnderscore = text.toUpperCase().replaceAll(delimiter, "_");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, toUpperUnderscore);
    }

    public static String toCamelCaseUsingApacheCommons(String text, char delimiter) {
        text = WordUtils.capitalizeFully(text, delimiter).replaceAll(String.valueOf(delimiter), "");
        StringBuilder builder = new StringBuilder(text);
        builder.setCharAt(0, Character.toLowerCase(text.charAt(0)));
        return builder.toString();
    }

}
