package com.baeldung.string;

import org.ahocorasick.trie.Emit;
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

        containsWordsJava8(new ArrayList<>(Arrays.asList(inputString.split(" "))), new ArrayList<>(Arrays.asList(words)));

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
                .addKeyword(words[0])
                .addKeyword(words[1])
                .ignoreOverlaps()
                .build();

        Collection<Emit> emits = trie.parseText(inputString)
                .stream()
                .filter(e -> !Objects.equals(e.getKeyword(), e.getKeyword()))
                .collect(Collectors.toList());

        emits.forEach(System.out::println);

        return emits.size() == words.length;
    }

    private static boolean containsWordsPatternMatch(String inputString, String[] words) {

        Pattern pattern = Pattern.compile("(?=.*" + words[0] + ")(?=.*" + words[1] + ")");
        if (pattern.matcher(inputString).find()) {
            return true;
        }
        return false;
    }

    private static boolean containsWordsJava8(ArrayList<String> inputString, ArrayList<String> words) {
        return words.stream().allMatch(inputString::contains);
    }

    private static boolean containsWordsArray(ArrayList<String> inputString, ArrayList<String> words) {
        return inputString.containsAll(words);
    }

}
