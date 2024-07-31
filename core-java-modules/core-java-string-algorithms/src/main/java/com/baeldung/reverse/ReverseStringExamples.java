package com.baeldung.reverse;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public static String reverseUsingIntStreamRangeMethod(String str) {
        if (str == null) {
            return null;
        }

        char[] charArray = str.toCharArray();
        return IntStream.range(0, str.length())
          .mapToObj(i -> charArray[str.length() - i - 1])
          .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
          .toString();
    }

    public static String reverseUsingStreamOfMethod(String str) {
        if (str == null) {
            return null;
        }

        return Stream.of(str)
          .map(string -> new StringBuilder(string).reverse())
          .collect(Collectors.joining());
    }

    public static String reverseUsingCharsMethod(String str) {
        if (str == null) {
            return null;
        }

        return str.chars()
          .mapToObj(c -> (char) c)
          .reduce("", (a, b) -> b + a, (a2, b2) -> b2 + a2);
    }

}
