package com.baeldung.hexagonal.bookfinder.domain;

public class Book {

    private String name;

    private String author;

    private String isbn;

    private String content;

    public Book(String name, String author, String isbn) {
        this.name = name;
        this.author = author;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object other) {
        if (other != null && other instanceof Book && this.getIsbn() != null) {
            return this.getIsbn()
                .equals(((Book) other).getIsbn());
        }
        return false;
    }

}
