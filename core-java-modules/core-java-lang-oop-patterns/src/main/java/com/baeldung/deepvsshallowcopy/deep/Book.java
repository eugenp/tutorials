package com.baeldung.deepvsshallowcopy.deep;

import com.baeldung.deepvsshallowcopy.Author;

public class Book {

    private String title;
    private Author author;

    public Book(Book book) {
        this(book.getTitle(), new Author(book.getAuthor()));
    }

    public Book(String title, Author author) {
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
