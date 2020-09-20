package com.baeldung.ports;

import domain.Book;

import java.util.List;

public interface BookRepository {

    public Book getBookById(String id);

    public List<Book> findAllBooksFromAuthor(String author);

}
