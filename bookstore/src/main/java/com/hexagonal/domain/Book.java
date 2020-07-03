package com.hexagonal.domain;

public class Book {

    private String name;
    private String isbn;
    private String author;

    @Override
    public String toString() {
        return "Book [name=" + name + ", ISBN=" + isbn + ", author=" + author + "]";
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
