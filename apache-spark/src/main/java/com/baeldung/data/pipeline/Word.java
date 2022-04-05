package com.baeldung.data.pipeline;

import java.io.Serializable;

public class Word implements Serializable {
    private static final long serialVersionUID = 1L;
    private String word;
    private int count;
    Word(String word, int count) {
        this.word = word;
        this.count = count;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}