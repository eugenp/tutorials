package com.baeldung.string;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.*;
import java.util.regex.Pattern;

public class MatchWords {

    public static void main(String[] args) {
        String[] items = {"hello", "Baeldung"};
        String inputString = "hello there, Baeldung";

        System.out.println(containsWords(inputString, items));

        System.out.println(java8(new ArrayList<>(Arrays.asList(inputString.split(" "))), new ArrayList<>(Arrays.asList(items))));

        System.out.println(patternMatch(inputString));

        ahoCorasick();

        wordIndices(inputString);
    }

    private static void wordIndices(String inputString) {
        Map<Integer, String> wordIndices = new TreeMap<>();
        List<String> words = new ArrayList<>();
        words.add("hello");
        words.add("Baeldung");

        for (String word : words) {
            int index = inputString.indexOf(word);

            if (index != -1) {
                wordIndices.put(index, word);
            }
        }

        wordIndices.keySet().forEach(System.out::println);
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

    private static void ahoCorasick() {
        Trie trie = Trie.builder()
                .onlyWholeWords()
                .addKeyword("hello")
                .addKeyword("Baeldung")
                .build();
        Collection<Emit> emits = trie.parseText("hello there, Baeldung");
        emits.forEach(System.out::println);
    }

    private static boolean patternMatch(String inputString) {
        Pattern pattern = Pattern.compile("(?=.*hello)(?=.*Baeldung)");
        if (pattern.matcher(inputString).find()) {
            return true;
        }
        return false;
    }

    private static boolean java8(ArrayList<String> inputString, ArrayList<String> items) {
        return items.stream().allMatch(inputString::contains);
    }

    private static boolean array(ArrayList<String> inputString, ArrayList<String> items) {
        return inputString.containsAll(items);
    }

}
