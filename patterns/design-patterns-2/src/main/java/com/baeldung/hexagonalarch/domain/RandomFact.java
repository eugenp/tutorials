package com.baeldung.hexagonalarch.domain;

public class RandomFact {
    private String id;
    private String content;

    public RandomFact(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "RandomFact { ID = " + id + ", content = " + content + "}";
    }
}
