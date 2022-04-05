package com.baeldung.spring.patterns.proxy;

public class Book {

    private String author;

    public Book(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
