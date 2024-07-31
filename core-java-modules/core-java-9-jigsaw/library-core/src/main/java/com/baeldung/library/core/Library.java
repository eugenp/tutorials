package com.baeldung.library.core;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    void removeBook(Book book) {
        books.remove(book);
    }

    protected void removeBookByAuthor(String author) {
        books.removeIf(book -> book.getAuthor().equals(author));
    }
}
