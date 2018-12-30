package com.baeldung.hexagonal.bookstore.utils;

import com.baeldung.hexagonal.bookstore.model.Book;

public class BookStoreData {

    public static Book fetchBook(String isbn) {
        return bookBuilder(isbn);
    }

    public static Book bookBuilder(String isbn) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAuthorName("Benjamin Graham");
        book.setTitle("The Intelligent Investor");
        return book;
    }
}
