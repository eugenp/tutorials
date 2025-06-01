package com.baeldung.countingchars;

import java.util.HashMap;
import java.util.Map;

public class CharacterFrequencyCounter {

    public static Map<Character, Integer> countCharacters(String input) {
        Map<Character, Integer> characterCountMap = new HashMap<>();

        for (char ch : input.toCharArray()) {
            characterCountMap.put(ch, characterCountMap.getOrDefault(ch, 0) + 1);
        }

        return characterCountMap;
    }
}
