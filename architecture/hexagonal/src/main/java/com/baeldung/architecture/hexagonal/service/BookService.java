package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.model.Book;

import java.util.List;

public interface BookService {

        List<Book> getAllBooks();

        Book saveBook(Book book);

        Book getBook(long isbn);

}
