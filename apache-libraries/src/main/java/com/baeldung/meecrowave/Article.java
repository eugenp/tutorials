package com.baeldung.meecrowave;

public class Article {
    private String name;
    private String author;

    public Article() {
    }

    public Article(String name, String author) {
        this.author = author;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}