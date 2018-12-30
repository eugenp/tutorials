package com.baeldung.hexagonal.bookstore.primaryport;

import com.baeldung.hexagonal.bookstore.model.Book;

public interface BookFinder {
    public Book findBook(String isbnNumber);
}
