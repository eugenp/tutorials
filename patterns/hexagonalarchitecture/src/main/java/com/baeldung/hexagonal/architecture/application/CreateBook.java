package com.baeldung.hexagonal.architecture.application;

import com.baeldung.hexagonal.architecture.domain.Book;

public class CreateBook {
    private Book book;

    public CreateBook(String title, String author, String isbn) {
        this.book = new Book(title, author, isbn);
    }

    public Book getBook() {
        return book;
    }
}
