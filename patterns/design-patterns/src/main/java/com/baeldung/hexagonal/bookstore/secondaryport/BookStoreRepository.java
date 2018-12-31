package com.baeldung.hexagonal.bookstore.secondaryport;

import com.baeldung.hexagonal.bookstore.entity.Book;

public interface BookStoreRepository {
    public Book findBookByIsbn(String isbnNumber);
}
