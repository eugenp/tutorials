package com.baeldung.microprofile.model;

public class Book {

    private String id;
    private String name;
    private String isbn;
    private String author;
    private int nbrePages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNbrePages() {
        return nbrePages;
    }

    public void setNbrePages(int nbrePages) {
        this.nbrePages = nbrePages;
    }
}
