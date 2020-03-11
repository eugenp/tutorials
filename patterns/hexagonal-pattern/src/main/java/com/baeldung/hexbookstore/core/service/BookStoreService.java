package com.baeldung.hexbookstore.core.service;

import com.baeldung.hexbookstore.core.BookStore;

import java.util.List;

public interface BookStoreService {

    void sellBook(Long id);

    void addBook(BookStore book);

    List<BookStore> listAllBooks();
}
