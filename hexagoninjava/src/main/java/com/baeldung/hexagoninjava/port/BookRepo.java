package com.baeldung.hexagoninjava.port;


import com.baeldung.hexagoninjava.core.domain.Book;

import java.util.List;

/**
 * @implNote It is an Outbound Port that will be used to communicate with outside world of core application
 */
public interface BookRepo {

    void createBook(Book book);

    Book getBook(String name);

    List<Book> getAllBooks();

}