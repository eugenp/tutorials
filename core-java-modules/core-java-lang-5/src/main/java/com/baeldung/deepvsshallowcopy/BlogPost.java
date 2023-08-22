package com.baeldung.deepvsshallowcopy;

import java.util.ArrayList;
import java.util.List;

public class BlogPost {
    private String title;
    private String content;
    private Author author;
    private List<String> tags;

    public BlogPost(String title, String content, Author author, List<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
    }

    public BlogPost(BlogPost src) {
        this(src.title, src.content, new Author(src.author.getFirstName(), src.author.getLastName()), new ArrayList<>(src.tags));
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Author getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }
}