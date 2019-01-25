package com.baeldung.hexagonal.service;

import java.util.List;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.exception.BookNotFoundException;

public interface BookManager {
    
    public Book addBook(Book book);

    public Book findBookByIsbn(String isbn) throws BookNotFoundException;

    public Book deleteBook(String isbn) throws BookNotFoundException;

    public List<Book> fetchAllBooks();

    public Book updateBook(Book book) throws BookNotFoundException;

}
