package com.baeldung.shallowdeepcopy;

public class Article {

    private String title;

    private Tag tag;

    public Article(String title, Tag tag) {
        this.title = title;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public Tag getTag() {
        return tag;
    }
}
