package com.baeldung.hexagonal.domain;

import java.util.Map;

public class TextInfo {

    private String text;

    private Map<Character, Integer> charFrequency;

    private Integer numberOfDistinctChars;

    public TextInfo(String text, Map<Character, Integer> charFrequency, Integer numberOfDistinctChars) {
        this.text = text;
        this.charFrequency = charFrequency;
        this.numberOfDistinctChars = numberOfDistinctChars;
    }

    public String getText() {
        return text;
    }

    public Map<Character, Integer> getCharFrequency() {
        return charFrequency;
    }

    public Integer getNumberOfDistinctChars() {
        return numberOfDistinctChars;
    }

    @Override
    public String toString() {
        return String.format("TextInfo for %d:\nCharFrequency: %s\nNum of DistinctChars: %d\n", text.hashCode(), charFrequency, numberOfDistinctChars);
    }
}
