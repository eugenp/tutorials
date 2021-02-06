package com.baeldung.booksapp.port;

import java.util.List;

import com.baeldung.booksapp.core.domain.Book;

public interface BookService {

    public void createBook(Book book);

    public Book getBook(String name);

    public List<Book> listBook();
}
