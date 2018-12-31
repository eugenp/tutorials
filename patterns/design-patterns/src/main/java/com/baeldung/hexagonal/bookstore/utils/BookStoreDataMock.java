package com.baeldung.hexagonal.bookstore.utils;

import com.baeldung.hexagonal.bookstore.entity.Book;

public class BookStoreDataMock {

    public static Book bookBuilder(String isbn) {
        Book book = new Book(isbn, "Benjamin Graham", "The Intelligent Investor");
        return book;
    }
}
