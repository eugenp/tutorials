package com.baeldung.booksapp.port;

import java.util.List;

import com.baeldung.booksapp.core.domain.Book;

public interface BookRepo {

    void createBook(Book book);

    public Book getBook(String name);

    public List<Book> getAllBooks();
}
