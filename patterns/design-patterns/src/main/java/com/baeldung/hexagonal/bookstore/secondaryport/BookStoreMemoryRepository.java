package com.baeldung.hexagonal.bookstore.secondaryport;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.hexagonal.bookstore.entity.Book;
import com.baeldung.hexagonal.bookstore.utils.BookStoreDataMock;

public class BookStoreMemoryRepository implements BookStoreRepository {

    Map<String, Book> books = new HashMap<String, Book>();

    public BookStoreMemoryRepository() {
        super();
        books.put("9780062312686", BookStoreDataMock.bookBuilder("9780062312686"));
    }

    @Override
    public Book findBookByIsbn(String isbnNumber) {
        return books.get(isbnNumber);
    }

}
