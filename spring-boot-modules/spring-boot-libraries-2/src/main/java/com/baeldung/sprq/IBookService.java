package com.baeldung.sprq;

import java.util.List;

public interface IBookService {
    Book getBookWithTitle(String title);

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Book book);

    boolean deleteBook(Book book);
}