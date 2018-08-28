package com.baeldung.algorithms.string;

public class EnglishAlphabetLetters {

    public static boolean checkStringForAllTheLetters(String input) {
        boolean[] visited = new boolean[26];

        int index = 0;

        for (int id = 0; id < input.length(); id++) {
            if ('a' <= input.charAt(id) && input.charAt(id) <= 'z') {
                index = input.charAt(id) - 'a';
            } else if ('A' <= input.charAt(id) && input.charAt(id) <= 'Z') {
                index = input.charAt(id) - 'A';
            }
            visited[index] = true;
        }

        for (int id = 0; id < 26; id++) {
            if (!visited[id]) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkStringForAllLetterUsingStream(String input) {
        long c = input.toLowerCase().chars().filter(ch -> ch >= 'a' && ch <= 'z').distinct().count();
        return c == 26;
    }

    public static boolean checkStringForAllLetterUsingRegex(String input) {
        // filter out all the characters except a-z
        input = input.replaceAll("[^a-zA-Z]", "");
        input = input.toLowerCase();
        // remove duplicate elements
        input = input.replaceAll("(.)(?=.*\\1)", "");
        return input.length() == 26;
    }

}