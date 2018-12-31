package com.baeldung.hexagonal.bookstore.primaryport;

import com.baeldung.hexagonal.bookstore.entity.Book;

public interface BookFinder {
    public Book findBook(String isbnNumber);
}
