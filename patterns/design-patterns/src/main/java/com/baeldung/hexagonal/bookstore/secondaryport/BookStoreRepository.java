package com.baeldung.hexagonal.bookstore.secondaryport;

import com.baeldung.hexagonal.bookstore.model.Book;

public interface BookStoreRepository {
    public Book findBookByIsbn(String isbnNumber);
}
