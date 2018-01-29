package com.baeldung.beaninjection.model;

/**
 * Created by ggallo on 23/01/2018.
 */
public class Article {

    private String title;
    private Author author;

    public Article(String title, Author author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
