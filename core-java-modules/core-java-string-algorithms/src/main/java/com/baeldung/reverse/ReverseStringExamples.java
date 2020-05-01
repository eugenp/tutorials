package com.baeldung.reverse;

import org.apache.commons.lang3.StringUtils;

public class ReverseStringExamples {

    public static String reverse(String input) {
        if (input == null) {
            return null;
        }

        String output = "";

        for (int i = input.length() - 1; i >= 0; i--) {
            output = output + input.charAt(i);
        }

        return output;
    }

    public static String reverseUsingStringBuilder(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder output = new StringBuilder(input).reverse();

        return output.toString();
    }

    public static String reverseUsingApacheCommons(String input) {
        return StringUtils.reverse(input);
    }

    public static String reverseTheOrderOfWords(String sentence) {
        if (sentence == null) {
            return null;
        }

        StringBuilder output = new StringBuilder();
        String[] words = sentence.split(" ");

        for (int i = words.length - 1; i >= 0; i--) {
            output.append(words[i]);
            output.append(" ");
        }

        return output.toString()
            .trim();
    }

    public static String reverseTheOrderOfWordsUsingApacheCommons(String sentence) {
        return StringUtils.reverseDelimited(sentence, ' ');
    }

}
