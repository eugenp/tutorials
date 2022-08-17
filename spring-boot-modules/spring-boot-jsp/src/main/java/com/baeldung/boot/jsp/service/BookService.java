package com.baeldung.boot.jsp.service;

import java.util.Collection;

import com.baeldung.boot.jsp.dto.Book;

public interface BookService {
    Collection<Book> getBooks();

    Book addBook(Book book);
}