package com.baeldung.spliterator;

public class Author {
    private String name;
    private int relatedArticleId;

    public Author(String name, int relatedArticleId) {
        this.name = name;
        this.relatedArticleId = relatedArticleId;
    }

    public int getRelatedArticleId() {
        return relatedArticleId;
    }

    public void setRelatedArticleId(int relatedArticleId) {
        this.relatedArticleId = relatedArticleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", relatedId: " + relatedArticleId + "]";
    }
}

