package com.baeldung.hexagonal;

import java.util.HashMap;

public class BookDaoMock implements BookDaoInterface {
    private HashMap<String, Book> books = new HashMap<String, Book>();

    public BookDaoMock() {
        books.put("mock", new Book("mock", "mock", "mock"));
    }

    public Book get(String isbn) {
        return books.get(isbn);
    }
}
