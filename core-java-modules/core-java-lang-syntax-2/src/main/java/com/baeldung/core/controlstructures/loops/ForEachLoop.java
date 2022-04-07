package com.baeldung.core.controlstructures.loops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForEachLoop {

    public static void main(String[] args) {

        int[] numbers = { 1, 2, 3, 4, 5 };
        List<String> wordsList = new ArrayList<>();
        wordsList.add("Java");
        wordsList.add("is");
        wordsList.add("great!");

        Set<String> wordsSet = new HashSet<>();
        wordsSet.addAll(wordsList);

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Java");
        map.put(2, "is");
        map.put(3, "great!");

        traverseArray(numbers);
        traverseList(wordsList);
        traverseSet(wordsSet);
        traverseMap(map);
    }

    private static void traverseMap(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("number: " + entry.getKey() + " - " + "word: " + entry.getValue());
        }
    }

    private static void traverseSet(Set<String> wordsSet) {
        for (String word : wordsSet) {
            System.out.println(word + " ");
        }
    }

    private static void traverseList(List<String> wordsList) {
        for (String word : wordsList) {
            System.out.println(word + " ");
        }
    }

    private static void traverseArray(int[] numbers) {
        for (int number : numbers) {
            System.out.println(number + " ");
        }
    }
}
