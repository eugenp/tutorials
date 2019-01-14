package com.baeldung.string;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Token;
import org.ahocorasick.trie.Trie;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchWords {

    public static void main(String[] args) {
        String[] words = {"hello", "Baeldung"};
        String inputString = "hello there, Baeldung";

        containsWords(inputString, words);

        containsWordsJava8(inputString, words);

        containsWordsPatternMatch(inputString, words);

        containsWordsAhoCorasick(inputString, words);

        containsWordsIndexOf(inputString, words);
    }

    private static boolean containsWordsIndexOf(String inputString, String[] words) {
        boolean found = true;
        for (String word : words) {
            int index = inputString.indexOf(word);
            if (index == -1) {
                found = false;
                break;
            }
        }
        return found;
    }

    private static boolean containsWords(String inputString, String[] items) {
        boolean found = true;
        for (String item : items) {
            if (!inputString.contains(item)) {
                found = false;
                break;
            }
        }
        return found;
    }

    private static boolean containsWordsAhoCorasick(String inputString, String[] words) {
        Trie trie = Trie.builder()
                .onlyWholeWords()
                .addKeywords(words)
                .build();

        Collection<Emit> emits = trie.parseText(inputString);
        emits.forEach(System.out::println);

        boolean found = true;
        for(String word : words) {
            boolean contains = Arrays.toString(emits.toArray()).contains(word);
            if (!contains) {
                found = false;
                break;
            }
        }

        return found;
    }

    private static boolean containsWordsPatternMatch(String inputString, String[] words) {

        StringBuilder regexp = new StringBuilder();
        for (String word : words) {
            regexp.append("(?=.*").append(word).append(")");
        }

        Pattern pattern = Pattern.compile(regexp.toString());
        if (pattern.matcher(inputString).find()) {
            return true;
        }
        return false;
    }

    private static boolean containsWordsJava8(String inputString, String[] words) {
        ArrayList inputStringList = new ArrayList<>(Arrays.asList(inputString.split(" ")));
        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(words));

        return wordsList.stream().allMatch(inputStringList::contains);
    }

    private static boolean containsWordsArray(String inputString, String[] words) {
        ArrayList inputStringList = new ArrayList<>(Arrays.asList(inputString.split(" ")));
        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(words));

        return inputStringList.containsAll(wordsList);
    }
}
