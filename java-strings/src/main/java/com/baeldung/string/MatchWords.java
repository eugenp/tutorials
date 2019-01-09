package com.baeldung.string;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

public class MatchWords {

    // *778*1# *778*00#
    public static void main(String[] args) {
        String[] items = {"hello", "Baeldung"};
        String inputString = "hello there, Baeldung";

        boolean isMatch = java8(inputString, new ArrayList<>(Arrays.asList(items)));

        System.out.println(isMatch);

        System.out.println(patternMatch(inputString));

        ahoCorasick();
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

    private static boolean java8(String inputString, ArrayList<String> items) {
        return Arrays.stream(inputString.split(" ")).allMatch(items::contains);
    }
}
