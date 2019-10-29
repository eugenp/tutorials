package com.baeldung.mockito;

import java.util.HashMap;
import java.util.Map;

class MyDictionary {

    private Map<String, String> wordMap;

    MyDictionary() {
        wordMap = new HashMap<>();
    }

    MyDictionary(Map<String, String> wordMap) {
        this.wordMap = wordMap;
    }

    public void add(final String word, final String meaning) {
        wordMap.put(word, meaning);
    }

    String getMeaning(final String word) {
        return wordMap.get(word);
    }
}
