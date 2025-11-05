package com.baeldung.streams.collectors;

import java.util.List;

public class Blog {

    private String authorName;
    private List<String> comments;

    public Blog(String authorName, String... comments) {
        this.authorName = authorName;
        this.comments = List.of(comments);
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public List<String> getComments() {
        return this.comments;
    }
}
