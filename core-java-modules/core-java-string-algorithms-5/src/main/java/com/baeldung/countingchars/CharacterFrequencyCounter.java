package com.baeldung.countingchars;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CharacterFrequencyCounter {

    public static Map<Character, Integer> countCharactersWithLoop(String input) {
        Map<Character, Integer> characterCountMap = new HashMap<>();

        for (char ch : input.toCharArray()) {
            characterCountMap.put(ch, characterCountMap.getOrDefault(ch, 0) + 1);
        }

        return characterCountMap;
    }

    public static Map<Character, Long> countCharactersWithStreams(String input) {
        return input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

}
