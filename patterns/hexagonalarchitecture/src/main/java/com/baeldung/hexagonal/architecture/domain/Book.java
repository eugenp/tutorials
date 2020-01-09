package com.baeldung.hexagonal.architecture.domain;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "[Title: " + this.title + ", Author: " + this.author + ". ISBN: " + this.isbn + " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return Objects.equals(this.title, book.title) && Objects.equals(this.author, book.author) && Objects.equals(this.isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn);
    }
}
