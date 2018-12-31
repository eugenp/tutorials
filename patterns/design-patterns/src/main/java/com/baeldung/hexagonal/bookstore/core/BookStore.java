package com.baeldung.hexagonal.bookstore.core;

import com.baeldung.hexagonal.bookstore.entity.Book;

public interface BookStore {
    Book getBook(String isbnNumber);
}
