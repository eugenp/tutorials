package com.baeldung.hexagonal.bookstore.core;

import com.baeldung.hexagonal.bookstore.model.Book;

public interface BookStore {
    Book getBook(String isbnNumber);
}
