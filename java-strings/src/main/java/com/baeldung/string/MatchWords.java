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

    public static boolean containsWordsIndexOf(String inputString, String[] words) {
        boolean found = true;
        for (String word : words) {
            if (inputString.indexOf(word) == -1) {
                found = false;
                break;
            }
        }
        return found;
    }

    public static boolean containsWords(String inputString, String[] items) {
        boolean found = true;
        for (String item : items) {
            if (!inputString.contains(item)) {
                found = false;
                break;
            }
        }
        return found;
    }

    public static boolean containsWordsAhoCorasick(String inputString, String[] words) {
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

    public static boolean containsWordsPatternMatch(String inputString, String[] words) {

        StringBuilder regexp = new StringBuilder();
        for (String word : words) {
            regexp.append("(?=.*").append(word).append(")");
        }

        Pattern pattern = Pattern.compile(regexp.toString());

        return pattern.matcher(inputString).find();
    }

    public static boolean containsWordsJava8(String inputString, String[] words) {
        List<String> inputStringList = Arrays.asList(inputString.split(" "));
        List<String> wordsList = Arrays.asList(words);

        return wordsList.stream().allMatch(inputStringList::contains);
    }

    public static boolean containsWordsArray(String inputString, String[] words) {
        List<String> inputStringList = Arrays.asList(inputString.split(" "));
        List<String> wordsList = Arrays.asList(words);

        return inputStringList.containsAll(wordsList);
    }
}
