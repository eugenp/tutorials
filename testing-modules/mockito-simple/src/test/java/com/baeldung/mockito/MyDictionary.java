package com.baeldung.mockito;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary {

    private Map<String, String> wordMap;

    public MyDictionary() {
        wordMap = new HashMap<>();
    }

    public MyDictionary(Map<String, String> wordMap) {
        this.wordMap = wordMap;
    }

    public void add(final String word, final String meaning) {
        wordMap.put(word, meaning);
    }

    public String getMeaning(final String word) {
        return wordMap.get(word);
    }
}
