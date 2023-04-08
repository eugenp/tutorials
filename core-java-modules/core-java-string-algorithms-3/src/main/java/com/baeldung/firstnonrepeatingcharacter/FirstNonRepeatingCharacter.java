package com.baeldung.firstnonrepeatingcharacter;

import java.util.HashMap;
import java.util.Map;

public class FirstNonRepeatingCharacter {
    public Character firstNonRepeatingCharBruteForce(String inputString) {
        if (null == inputString || inputString.isEmpty()) {
            return null;
        }
        for (Character c : inputString.toCharArray()) {
            int indexOfC = inputString.indexOf(c);
            if (indexOfC == inputString.lastIndexOf(c)) {
                return c;
            }
        }
        return null;
    }

    public Character firstNonRepeatingCharBruteForceNaive(String inputString) {
        if (null == inputString || inputString.isEmpty()) {
            return null;
        }
        for (int outer = 0; outer < inputString.length(); outer++) {
            boolean repeat = false;
            for (int inner = 0; inner < inputString.length(); inner++) {
                if (inner != outer && inputString.charAt(outer) == inputString.charAt(inner)) {
                    repeat = true;
                    break;
                }
            }
            if (!repeat) {
                return inputString.charAt(outer);
            }
        }
        return null;
    }

    public Character firstNonRepeatingCharWithMap(String inputString) {
        if (null == inputString || inputString.isEmpty()) {
            return null;
        }
        Map<Character, Integer> frequency = new HashMap<>();
        for (int outer = 0; outer < inputString.length(); outer++) {
            char character = inputString.charAt(outer);
            frequency.put(character, frequency.getOrDefault(character, 0) + 1);
        }
        for (Character c : inputString.toCharArray()) {
            if (frequency.get(c) == 1) {
                return c;
            }
        }
        return null;
    }

    public Character firstNonRepeatingCharWithArray(String inputString) {
        if (null == inputString || inputString.isEmpty()) {
            return null;
        }
        int[] frequency = new int[26];
        for (int outer = 0; outer < inputString.length(); outer++) {
            char character = inputString.charAt(outer);
            frequency[character - 'a']++;
        }
        for (Character c : inputString.toCharArray()) {
            if (frequency[c - 'a'] == 1) {
                return c;
            }
        }
        return null;
    }
}