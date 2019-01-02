package com.baeldung.string;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Panagram {

    public static boolean isPanagram(String str) {
        if (str == null)
            return false;
        Boolean[] alphabetMarker = new Boolean[26];
        Arrays.fill(alphabetMarker, false);
        int alphabetIndex = 0;
        str = str.toUpperCase();
        for (int i = 0; i < str.length(); i++) {
            if ('A' <= str.charAt(i) && str.charAt(i) <= 'Z') {
                alphabetIndex = str.charAt(i) - 'A';
                alphabetMarker[alphabetIndex] = true;
            }
        }
        for (boolean index : alphabetMarker) {
            if (!index)
                return false;
        }
        return true;
    }

    public static boolean isPanagramWithStreams(String str) {
        if (str == null)
            return false;
        
        // filtered character stream
        str = str.toUpperCase();
        Stream<Character> filteredCharStream = str.chars()
            .filter(item -> ((item >= 'A' && item <= 'Z') || (item >= 'a' && item <= 'z')))
            .mapToObj(c -> (char) c);
        Map<Character, Boolean> alphabetMap = filteredCharStream.collect(Collectors.toMap(item -> item, k -> Boolean.TRUE, (p1, p2) -> p1));

        return (alphabetMap.size() == 26);
    }

    public static boolean isPerfectPanagram(String str) {
        if (str == null)
            return false;
        
        // filtered character stream
        str = str.toUpperCase();
        Stream<Character> filteredCharStream = str.chars()
            .filter(item -> ((item >= 'A' && item <= 'Z') || (item >= 'a' && item <= 'z')))
            .mapToObj(c -> (char) c);
        Map<Character, Long> alphabetFrequencyMap = filteredCharStream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return (alphabetFrequencyMap.size() == 26 && alphabetFrequencyMap.values()
            .stream()
            .allMatch(item -> item == 1));
    }
}
