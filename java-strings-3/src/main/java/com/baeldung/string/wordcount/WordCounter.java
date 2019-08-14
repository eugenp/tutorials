package com.baeldung.string.wordcount;

import java.util.StringTokenizer;

public class WordCounter {
    public static void main(String[] args) {
        
    }

    public static int countWordsUsingRegex(String arg) {
        if (arg == null) {
            return 0;
        }
        final String[] words = arg.split("\\pP|\\s+");
        return words.length;
    }

    public static int countWordsUsingTokenizer(String arg) {
        if (arg == null) {
            return 0;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(arg);
        return stringTokenizer.countTokens();
    }

    public static int countWordsManually(String arg) {
        if (arg == null) {
            return 0;
        }

        int count = 0;

        boolean isAWord = false;
        int argEnd = arg.length() - 1;

        for (int i = 0; i < arg.length(); i++) {
            if (Character.isLetter(arg.charAt(i)) && i != argEnd) {
                isAWord = true;
            } else if (!Character.isLetter(arg.charAt(i)) && isAWord) {
                count++;
                isAWord = false;
            } else if (Character.isLetter(arg.charAt(i)) && i == argEnd) {
                count++;
            }
        }
        return count;
    }
}
