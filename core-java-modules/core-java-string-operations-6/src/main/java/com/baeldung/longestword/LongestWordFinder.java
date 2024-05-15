package com.baeldung.longestword;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LongestWordFinder {

    public Optional<String> findLongestWord(String sentence) {
        return Optional.ofNullable(sentence)
          .filter(string -> !string.trim()
            .isEmpty())
          .map(string -> string.split("\\s"))
          .map(Arrays::asList)
          .map(list -> Collections.max(list, Comparator.comparingInt(String::length)));
    }

    public List<String> findLongestWords(String sentence) {
        if (sentence == null || sentence.trim()
          .isEmpty()) {
            return Collections.emptyList();
        }
        String[] words = sentence.split("\\s");
        int maxWordLength = Arrays.stream(words)
          .mapToInt(String::length)
          .max()
          .orElseThrow();
        return Arrays.stream(words)
          .filter(word -> word.length() == maxWordLength)
          .collect(Collectors.toList());
    }

}
