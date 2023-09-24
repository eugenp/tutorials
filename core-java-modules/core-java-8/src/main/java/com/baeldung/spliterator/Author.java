package com.baeldung.spliterator;

public class Author {
    private final String name;
    private final int relatedArticleId;

    public Author(String name, int relatedArticleId) {
        this.name = name;
        this.relatedArticleId = relatedArticleId;
    }

    public int getRelatedArticleId() {
        return relatedArticleId;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", relatedId: " + relatedArticleId + "]";
    }
}

