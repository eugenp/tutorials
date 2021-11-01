package com.baeldung.hexagoninjava.port;


import com.baeldung.hexagoninjava.core.domain.Book;

import java.util.List;

/**
 * @implNote BookService is an Inbound port will expose the application to the outside world
 */

public interface BookService {

    public void createBook(Book book);

    public Book getBook(String name);

    public List<Book> listBook();

}